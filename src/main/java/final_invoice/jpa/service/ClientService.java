package final_invoice.jpa.service;

import final_invoice.jpa.model.ClientModel;
import final_invoice.jpa.model.ClientDTO;
import final_invoice.jpa.model.InvoiceModel;
import final_invoice.jpa.exception.ClientAlreadyRegisteredException;
import final_invoice.jpa.exception.ClientNotFoundException;
import final_invoice.jpa.repository.ClientRepository;
import final_invoice.jpa.validateData.ClientValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


import java.util.ArrayList;


@Service
public class ClientService {
    @Autowired
    ClientRepository clientRepository;

    //crear cliente
    public ClientModel create(ClientModel newClient) throws ClientAlreadyRegisteredException {
        ClientValidator clientValidator = new ClientValidator();
        clientValidator.validate(newClient);
        Optional<ClientModel> clientOp = this.clientRepository.findByDocNumber(newClient.getDocNumber());
        this.ClientExist(clientOp, "El número de documento ingresado  \"" +newClient.getDocNumber()+ "\" corresponde a otro cliente.");
        return this.clientRepository.save(newClient);
    }

    // obtener la lista de todos los clientes
    public List<ClientDTO> findAll() {
        List<ClientModel> clientList = this.clientRepository.findAll();
        //Depuro la lista sacando los que no estan activos
//        lista.removeIf(item -> item.getActive()== false);
        List<ClientDTO> listDTO = this.returnListDTO(clientList);
        return listDTO;
    }

    // buscar cliente por ID
        public ClientModel findById(Integer id) throws Exception {
        this.CheckId(id);
        Optional<ClientModel> clientOp = this.clientRepository.findById(id);
        this.ClientIsEmpty(clientOp, "El ID ingresado no corresponde a ningún cliente.");
//        CheckStatus(clientOp.get());
//        return clientOp.get();
        return clientOp.orElse(null);
//        return this.returnClientDTO(clientOp.get());
    }

    // buscar cliente por número de documento
    public ClientDTO findByDocNumber(String docNumber) throws Exception {
        Optional<ClientModel> clientOp = this.clientRepository.findByDocNumber(docNumber);
        this.ClientIsEmpty(clientOp, "Cliente no encontrado con el número de documento: " + docNumber);
        return this.returnClientDTO(clientOp.get());
    }

    // editar un cliente
    public ClientModel update(ClientModel newData, Integer id) throws Exception {
        this.CheckId(id);
        Optional<ClientModel> clientOp = this.clientRepository.findById(id);
        this.ClientIsEmpty(clientOp, "El ID ingresado no corresponde a ningún cliente.");
        Optional<ClientModel> clientDoc = this.clientRepository.findByDocNumber(newData.getDocNumber());
        //Validacion para que no se repita el numero de documento en la base
        if (clientDoc.isPresent()) {
            if (!clientOp.get().getDocNumber().equals(newData.getDocNumber())) {
                this.ClientExist(clientOp, "El número de documento ingresado  \"" +newData.getDocNumber()+ "\" corresponde a otro cliente.");
            }
        }
        ClientModel clientUpdated = clientOp.get();
        clientUpdated.setName(newData.getName());
        clientUpdated.setSurname(newData.getSurname());
        clientUpdated.setDocNumber(newData.getDocNumber());
        if (newData.getName() == null || newData.getSurname() == null || newData.getDocNumber() == null) {
            throw new IllegalArgumentException("Falta información. Para la modificación de un cliente debe ingresar todos sus campos.");
        }
        return this.clientRepository.save(clientUpdated);
    }

    // elimnar un cliente
    public String deleteById(Integer id) throws Exception {
        this.CheckId(id);
        Optional<ClientModel> clientOp = this.clientRepository.findById(id);
        this.ClientIsEmpty(clientOp, "El ID " +id+" ingresado no corresponde a ningún cliente.");
        this.clientRepository.deleteById(id);
        return "Cliente eliminado correctamente.";
    }

    public void ClientExist(Optional clientOp, String message) throws ClientAlreadyRegisteredException {
        if (clientOp.isPresent()) {
            throw new ClientAlreadyRegisteredException(message);
        }
    }

    public void ClientIsEmpty(Optional clientOp, String message) throws ClientNotFoundException {
        if (clientOp.isEmpty()) {
            throw new ClientNotFoundException(message);
        }
    }

    public void CheckId(Integer id) throws Exception {
        if (id <= 0) {
            throw new Exception("El id " + id + " no es válido. Debe ser mayor o igual a 1.");
        }
    }

    public List<ClientDTO> returnListDTO(List<ClientModel> list) {
        List<ClientDTO> clientDTOList = new ArrayList<>();
        for (ClientModel item : list
        ) {
            clientDTOList.add(this.returnClientDTO(item));
        }
        return clientDTOList;
    }

    public ClientDTO returnClientDTO(ClientModel item) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(item.getId());
        clientDTO.setCompleteName(item.getName() + " " + item.getSurname());
        clientDTO.setDocNumber(item.getDocNumber());
        //Aca tengo que hacer DTO de invoices
        List<InvoiceModel> invoicesList = item.getInvoiceModel();
        List<Integer> idInvoicesList = new ArrayList<>();

        if (invoicesList != null) {
            for (InvoiceModel invoiceItem : invoicesList) {
                idInvoicesList.add(invoiceItem.getId());
            }
        }
        clientDTO.setInvoicesId(idInvoicesList);
        return clientDTO;
    }
}

