package final_invoice.jpa.service;

import final_invoice.jpa.exception.InsufficientStockException;
import final_invoice.jpa.exception.InvoiceNotFoundException;
import final_invoice.jpa.model.InvoiceDetailsModel;
import final_invoice.jpa.repository.InvoiceDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;



@Service
public class InvoiceDetailsService {
    @Autowired
    InvoiceDetailsRepository invoiceDetailsRepository;
    @Autowired
    ProductService productService;

    public InvoiceDetailsModel create(InvoiceDetailsModel newDetails) throws InsufficientStockException {
        Integer stock = newDetails.getProductModel().getStock();
        Integer amountToAdd = newDetails.getQuantity();
        if (amountToAdd > stock) {
            throw new InsufficientStockException("No hay suficiente stock para realizar la venta del producto con ID: " + newDetails.getProductModel().getId());
        }
        newDetails.getProductModel().setStock(stock-amountToAdd);

        return this.invoiceDetailsRepository.save(newDetails);
    }

    public List<InvoiceDetailsModel> findAll() {
        return  this.invoiceDetailsRepository.findAll();
    }

    public InvoiceDetailsModel findById(Integer id) throws Exception {
        if(id <= 0) {
            throw new Exception("El id" + id + " no es válido. Debe ser mayor o igual a 1.");
        }
        Optional<InvoiceDetailsModel> invoiceDetailOp = this.invoiceDetailsRepository.findById(id);
        if (invoiceDetailOp.isEmpty()) {
            throw new InvoiceNotFoundException("La información del detalles de la factura está vacía con el ID: "+id+". Intenta nuevamente con un nuevo ID.");
        }
        return this.invoiceDetailsRepository.getReferenceById(id);
    }
}
