package final_invoice.jpa.service;


import final_invoice.jpa.exception.ProductAlreadyExistsException;
import final_invoice.jpa.exception.ProductNotFoundException;
import final_invoice.jpa.model.ProductModel;
import final_invoice.jpa.repository.ProductRepository;
import final_invoice.jpa.validateData.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    // crear producto
    public ProductModel create(ProductModel newProduct) throws ProductAlreadyExistsException, IllegalArgumentException {
        Optional<ProductModel> productOp = this.productRepository.findByCode(newProduct.getCode());
        this.isPresent(productOp);
        ProductValidator.validate(newProduct);
        return this.productRepository.save(newProduct);
    }

    // buscar producto por ID
    public ProductModel findById(Integer id) throws Exception {
        this.checkId(id);
        Optional<ProductModel> productOp = this.productRepository.findById(id);
        this.isEmpty(productOp, String.valueOf(id));
        return productOp.orElse(null);
    }

    // buscar producto por codigo
    public ProductModel findByCode(String code) throws ProductNotFoundException{
        Optional<ProductModel> productOp = this.productRepository.findByCode(code);
        this.isEmpty(productOp, code);
        return productOp.orElse(null);
    }

    // obtener la lista de todos los productos
    public List<ProductModel> findAll(){
        return this.productRepository.findAll();
    }

    // editar producto
    public ProductModel update(ProductModel newData, Integer id) throws Exception{
        this.checkId(id);
        Optional<ProductModel> productOp = this.productRepository.findById(id);
        if (productOp.isEmpty()) {
            throw new ProductNotFoundException("El producto que deseas cambiar con el ID "+id+" no existe.");
        } else {
            if (!(newData.getCode().equals(productOp.get().getCode()))) {
                Optional<ProductModel> prodOP = this.productRepository.findByCode(newData.getCode());
                if (prodOP.isPresent()){
                    throw new ProductAlreadyExistsException("El código \""+newData.getCode()+ "\" ingresado ya existe en otro producto. Por favor, ingrese otro.");
                }
            }
            ProductModel updatedProduct = productOp.get();
            updatedProduct.setDescription( newData.getDescription() );
            updatedProduct.setCode( newData.getCode() );
            updatedProduct.setStock(newData.getStock());
            updatedProduct.setPrice(newData.getPrice());
            return this.productRepository.save(updatedProduct);
        }
    }

    // elimnar un producto
    public String deleteById(Integer id) throws Exception {
        this.checkId(id);
        Optional<ProductModel> productOp = this.productRepository.findById(id);
        if (productOp.isEmpty()){
            throw new ProductNotFoundException("El producto que deseas eliminar con el ID "+id+" no existe.");
        }
        this.productRepository.deleteById(id);
        return "Producto eliminado exitosamente.";
    }

    public void checkId(Integer id) throws Exception {
        if (id <= 0){
            throw new Exception("El id" + id + " no es válido. Debe ser mayor o igual a 1.");
        }
    }

    public void isPresent(Optional productOp) throws ProductAlreadyExistsException {
        if (productOp.isPresent()) {
            throw new ProductAlreadyExistsException("El producto que deseas agregar, ya existe en la base de datos.");
        }
    }

    public void isEmpty(Optional productOp, String data) throws ProductNotFoundException {
        if (productOp.isEmpty()){
            throw new ProductNotFoundException("El producto solicitado con el ID o código: " + data+ "no existe en la base de datos." );
        }

    }
}
