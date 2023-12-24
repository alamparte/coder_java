package final_invoice.jpa.controller;

import final_invoice.jpa.service.ProductService;
import final_invoice.jpa.model.ProductModel;
import final_invoice.jpa.exception.ProductNotFoundException;
import final_invoice.jpa.exception.ProductAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    // crear producto
    @PostMapping(path = "/create-product")
    public ResponseEntity<ProductModel> create(@RequestBody ProductModel newProduct) throws ProductAlreadyExistsException {
        return new ResponseEntity<>(this.productService.create(newProduct), HttpStatus.CREATED );
    }

    // buscar producto por ID
    @GetMapping(path = "/getById/{id}")
    public ResponseEntity<ProductModel> findById(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(this.productService.findById(id),HttpStatus.OK );
    }
    // buscar producto por código
    @GetMapping(path = "/getByCode/{code}")
    public ResponseEntity<ProductModel> findByCode(@PathVariable String code) throws ProductNotFoundException {
        return new ResponseEntity<>(this.productService.findByCode(code), HttpStatus.NOT_FOUND);
    }
    // obtener la lista de todos los productos
    @GetMapping(path = "/products")
    public ResponseEntity<List<ProductModel>> findAll() {
        return new ResponseEntity<>(this.productService.findAll(), HttpStatus.OK);
    }

    // editar un producto
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ProductModel> update(@RequestBody ProductModel productUpdate, @PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(this.productService.update(productUpdate, id), HttpStatus.OK);
    }

    // elimnar un producto
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(this.productService.deleteById(id),HttpStatus.OK);
    }
}


