package final_invoice.jpa.model;

import lombok.Data;

@Data
public class DetailsDTO {
    private String product;
    private String code;
    private int quantity;
    private double price;
    private double subTotal;
}
