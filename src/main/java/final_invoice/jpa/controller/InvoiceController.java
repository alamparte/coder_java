package final_invoice.jpa.controller;


import final_invoice.jpa.service.InvoiceService;
import final_invoice.jpa.model.InvoiceModel;
import final_invoice.jpa.model.InvoiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/invoice")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    //post método - crear factura
    @PostMapping(path = "/create-invoice")
    public ResponseEntity<InvoiceDTO> createInvoice(@RequestBody InvoiceModel newInvoice) throws Exception{
        return new ResponseEntity<>(this.invoiceService.createInvoice(newInvoice), HttpStatus.CREATED );
    }

    // obtener todas las facturas disponibles
     @GetMapping(path = "/invoices")
    public ResponseEntity<List<InvoiceDTO>> findAll() {
        return new ResponseEntity<>(this.invoiceService.findAll(),HttpStatus.OK);
    }

    // obtener factura por ID
    @GetMapping(path = "/{id}")
    public ResponseEntity<InvoiceDTO> findById(@PathVariable Integer id)  throws Exception{
        return new ResponseEntity<>(this.invoiceService.findById(id),HttpStatus.OK);
    }

}
