package final_invoice.jpa.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import java.util.List;
import jakarta.persistence.*;
@Data
@Entity
@Table(name = "clients")
public class ClientModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String surname;
    @Column(name = "doc_number")
    private String docNumber;
    @JsonManagedReference
    @OneToMany(mappedBy = "clientId", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("clientId")
    private List<InvoiceModel> invoiceModel;
}

