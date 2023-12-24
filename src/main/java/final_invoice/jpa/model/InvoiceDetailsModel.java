package final_invoice.jpa.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import jakarta.persistence.*;

@Data
@Entity
@Table(name = "invoice_details")
public class InvoiceDetailsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private InvoiceModel invoiceModel;
    @ManyToOne
    @JoinColumn(name = "products_id", nullable = false)
    private ProductModel productModel;
    private int quantity;
    private double price;
}


