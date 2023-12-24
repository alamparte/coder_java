package final_invoice.jpa.controller;


import final_invoice.jpa.exception.ClientAlreadyRegisteredException;
import final_invoice.jpa.model.ClientModel;
import final_invoice.jpa.model.ClientDTO;
import final_invoice.jpa.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "/api/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    //post método - crear cliente
    @PostMapping(path = "/create-client")
    public ResponseEntity<ClientModel> create(@RequestBody ClientModel newClient) throws ClientAlreadyRegisteredException {
        return new ResponseEntity<>(this.clientService.create(newClient), HttpStatus.CREATED );
    }

    // obtener la lista de todos los clientes
    @GetMapping(path = "/clients")
    public ResponseEntity<List<ClientDTO>> findAll() {
        return new ResponseEntity<>(this.clientService.findAll(), HttpStatus.OK);
    }

    // buscar cliente por ID
    @GetMapping(path = "/{id}")
//    public ResponseEntity<ClientDTO> findById(@PathVariable Integer id) throws Exception {
        public ResponseEntity<ClientModel> findById(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(this.clientService.findById(id), HttpStatus.OK);
    }

    // buscar cliente por número de documento
    @GetMapping(path = "/identification/{docNumber}")
    public ResponseEntity<ClientDTO> findByDocNumber(@PathVariable String docNumber) throws Exception {
        return new ResponseEntity<>(this.clientService.findByDocNumber(docNumber),HttpStatus.OK);
    }

    // editar un cliente
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ClientModel> update(@RequestBody ClientModel clientUpdated,@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(this.clientService.update(clientUpdated,id),HttpStatus.OK);
    }

    // eliminar un cliente
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(this.clientService.deleteById(id), HttpStatus.OK) ;
    }
}

