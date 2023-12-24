package final_invoice.jpa.validateData;

import final_invoice.jpa.model.ProductModel;

public class ProductValidator {
    public static void validate(ProductModel product) throws IllegalArgumentException{

        if (product == null) {
            throw new IllegalArgumentException("El cliente es nulo o el argumento no es válido.");
        }

        validateStringData("description", product.getDescription());
        validateStringData("código", product.getCode());

        if(product.getStock() < 0 ) {
            throw new IllegalArgumentException("Stock no puede ser negativo.");
        }

        if(product.getPrice() <= 0.01 ) {
            throw new IllegalArgumentException("El precio debe ser mayor que 0.01");
        }

       if(product.getCode().length() < 3) {
            throw new IllegalArgumentException("El código del producto debe ser de un mínimo de 3 carácteres.");
        }
    }
    private static void validateStringData(String attribute, String stringData) {
        if(stringData.isBlank()) {
            throw new IllegalArgumentException("El campo " + attribute + " no es válido o se encuentra vacío.");
        }
    }
}
