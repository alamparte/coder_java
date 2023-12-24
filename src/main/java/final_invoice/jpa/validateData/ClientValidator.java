package final_invoice.jpa.validateData;

import final_invoice.jpa.model.ClientModel;
import org.springframework.stereotype.Component;

@Component
public class ClientValidator {
    public void validate(ClientModel client) {
        if (client == null) {
            throw new IllegalArgumentException("El cliente es nulo o el argumento no es válido.");
        }

        if (client.getDocNumber().length() < 8) {
            throw new IllegalArgumentException("DNI no válido. Cantidad mínima de carácteres es 8 dígitos.");
        }

        this.validateStringData("name", client.getName());
        this.validateStringData("lastname", client.getSurname());
        this.validateStringData("docNumber", client.getDocNumber());
    }

    private void validateStringData(String attribute, String stringData) {
        if (stringData.isBlank()) {
            throw new IllegalArgumentException("El campo " + attribute + " no es válido o se encuentra vacío.");
        }
    }
}
