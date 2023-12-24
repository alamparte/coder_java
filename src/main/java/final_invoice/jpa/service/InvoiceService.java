package final_invoice.jpa.service;


import final_invoice.jpa.exception.InsufficientStockException;
import final_invoice.jpa.exception.InvoiceDetailsNotFoundException;
import final_invoice.jpa.exception.InvoiceNotFoundException;
import final_invoice.jpa.model.*;
import final_invoice.jpa.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.Optional;
import java.util.*;

@Service
public class InvoiceService {

    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    ClientService clientService;
    @Autowired
    InvoiceDetailsService invoiceDetailsService;
    @Autowired
    ProductService productService;

    // crear factura
    public InvoiceDTO createInvoice(InvoiceModel newData) throws Exception {
        InvoiceModel newInvoice = new InvoiceModel();
        newInvoice.setCreatedAt(LocalDate.now());
        // obtener id client
        Integer clientId = newData.getClientId().getId();
        // buscar cliente
        ClientModel clientToAdd = clientService.findById(clientId);
        newInvoice.setClientId(clientToAdd);
        // validar data
        this.validateInvoicesDetailsById(newData.getInvoiceDetails());
        // guardar data obtenida
        InvoiceModel invoiceSaved = this.invoiceRepository.save(newInvoice);
        List<InvoiceDetailsModel> detailsToAdd = new ArrayList<>();

        for (InvoiceDetailsModel invoiceDetail: newData.getInvoiceDetails()
        ) {
            ProductModel productToAdd = productService.findById(invoiceDetail.getProductModel().getId());
            // se crea un nuevo detalle y se agregan los datos necesarios
            InvoiceDetailsModel newDetail = new InvoiceDetailsModel();
            newDetail.setProductModel(productToAdd);
            newDetail.setQuantity(invoiceDetail.getQuantity());
            newDetail.setPrice(productToAdd.getPrice());
            newDetail.setInvoiceModel(invoiceSaved);

            // crear la lista
            InvoiceDetailsModel newDetailToAdd = this.invoiceDetailsService.create(newDetail);
            detailsToAdd.add(newDetailToAdd);
        }
        // actualizar el total de la factura con los details nuevos.
        newInvoice.setInvoiceDetails(detailsToAdd);
        double totalPrice = 0;
        for (InvoiceDetailsModel item: detailsToAdd
        ) {
            totalPrice = totalPrice + (item.getQuantity() * item.getProductModel().getPrice());
        }
        newInvoice.setTotal(totalPrice);

        this.invoiceRepository.save(newInvoice);
        // crear invoiceDTO
        InvoiceDTO invoiceDTO = this.returnInvoiceDTO(newInvoice);
        List<DetailsDTO> products = new ArrayList<>();
        // recorrer el detalle de factura original
        // // crear nuevos detailsDTO e incluirlos en el listado
        for (InvoiceDetailsModel item : newInvoice.getInvoiceDetails()) {
            products.add(this.returnDetailsDTO(item));
        }
        // agregar la lista de productos con la info filtrada
        invoiceDTO.setProducts(products);
        return invoiceDTO;
    }

    // obtener todas las facturas disponibles
    public List<InvoiceDTO> findAll(){
        List<InvoiceModel> invoiceModelList = this.invoiceRepository.findAll();
        // crear lista de invoiceDTO
        List<InvoiceDTO> invoiceDTOList = new ArrayList<>();
        for (InvoiceModel  invoiceItem : invoiceModelList) {
            // crear lista details
            List<DetailsDTO> detailsDTOList = new ArrayList<>();
            for (InvoiceDetailsModel detailItem: invoiceItem.getInvoiceDetails()
            ) {
                detailsDTOList.add(this.returnDetailsDTO(detailItem));
            }
            InvoiceDTO invoiceDTO = this.returnInvoiceDTO(invoiceItem);
            // Agrega la lista de detailsDTO al invoiceDTO
            invoiceDTO.setProducts(detailsDTOList);
            invoiceDTOList.add(invoiceDTO);
        }
        return invoiceDTOList;
    }

    // obtener factura por ID
    public InvoiceDTO findById(Integer id) throws Exception{
        if(id <= 0) {
            throw new Exception("El id" + id + " no es válido. Debe ser mayor o igual a 1.");
        }
        Optional<InvoiceModel> invoiceOp = this.invoiceRepository.findById(id);
        if (invoiceOp.isEmpty()) {
            throw new InvoiceNotFoundException("Factura no encontrada con el ID: "+id);
        }
        InvoiceModel invoice = invoiceOp.get();
        // obtener el invoiceDTO
        InvoiceDTO invoiceDTO = this.returnInvoiceDTO(invoice);
        List<DetailsDTO> products = new ArrayList<>();
        // recorrer el invoiceDetails original,
        // //para crear nuevos detailsDTO e incluirlos en el listado
        for (InvoiceDetailsModel item : invoice.getInvoiceDetails()) {
            products.add(this.returnDetailsDTO(item));
        }
        // agregar la lista de productos con la info filtrada
        invoiceDTO.setProducts(products);
        return invoiceDTO;
    }

    public void validateInvoicesDetailsById(List<InvoiceDetailsModel> newInvoicesDetailList)
            throws Exception{
        // chequear que no llegue vacía la lista
        if (newInvoicesDetailList.isEmpty()) {
            throw new InvoiceDetailsNotFoundException("La información del detalles de la factura está vacía. Intenta nuevamente.");
        }
        // chequear duplicados - stock - id
        Set<InvoiceDetailsModel> checkDuplicates = new HashSet<>();
        for (InvoiceDetailsModel newDetail: newInvoicesDetailList
        ) {
            ProductModel checkProduct = this.productService.findById(newDetail.getProductModel().getId());
            // chequear stock
            if( newDetail.getQuantity() > checkProduct.getStock()) {
                throw new InsufficientStockException("No hay suficiente stock para realizar la venta del producto con ID: " + newDetail.getProductModel().getId() );
            }
            checkDuplicates.add(newDetail);
        }
        if(checkDuplicates.size() != newInvoicesDetailList.size())  {
            throw new IllegalArgumentException("Producto duplicado en la lista.");
        }
    }

    public void validateInvoicesDetailsByCode (List<InvoiceDetailsModel> newInvoicesDetailList)
            throws Exception {
        // chequear que no llegue vacía la lista
        if (newInvoicesDetailList.isEmpty()) {
            throw new InvoiceDetailsNotFoundException("La información del detalles de la factura está vacía. Intenta nuevamente.");
        }
        // chequear duplicados - stock - id
        Set<InvoiceDetailsModel> checkDuplicates = new HashSet<>();
        for (InvoiceDetailsModel newDetail: newInvoicesDetailList
        ) {
            ProductModel checkProduct = this.productService.findByCode(newDetail.getProductModel().getCode());
            // chequear stock
            if( newDetail.getQuantity() > checkProduct.getStock()) {
                throw new InsufficientStockException("Insufficient stock in product ID=" + newDetail.getProductModel().getId() );
            }
            checkDuplicates.add(newDetail);
        }
        if(checkDuplicates.size() != newInvoicesDetailList.size())  {
            throw new IllegalArgumentException("Producto duplicado en la lista.");
        }
    }

    public InvoiceDTO returnInvoiceDTO(InvoiceModel invoice) {
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setId(invoice.getId());
        invoiceDTO.setCreatedAt(invoice.getCreatedAt());
        invoiceDTO.setClientId(invoice.getClientId().getId());
        invoiceDTO.setClientName(invoice.getClientId().getName() + " " + invoice.getClientId().getSurname());
        invoiceDTO.setTotal(invoice.getTotal());
        return invoiceDTO;
    }

    public DetailsDTO returnDetailsDTO(InvoiceDetailsModel detail) {
        DetailsDTO detailsDTO = new DetailsDTO();
        detailsDTO.setCode(detail.getProductModel().getCode());
        detailsDTO.setProduct(detail.getProductModel().getDescription());
        detailsDTO.setQuantity(detail.getQuantity());
        detailsDTO.setPrice(detail.getPrice());
        detailsDTO.setSubTotal(detail.getPrice() * detail.getQuantity());
        return detailsDTO;
    }
}




