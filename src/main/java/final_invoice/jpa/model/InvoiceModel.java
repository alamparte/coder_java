package final_invoice.jpa.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

//@Data
@Entity
@Table(name = "invoice")
public class InvoiceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "clients_id")
    @JsonIgnoreProperties("invoiceModel")
    private ClientModel clientId;
    @Column(name = "created_at")
    private LocalDate createdAt;
    private double total;
    @JsonManagedReference
    @OneToMany(mappedBy = "invoiceModel")
    private List<InvoiceDetailsModel> invoiceDetails;


    public InvoiceModel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ClientModel getClientId() {
        return clientId;
    }

    public void setClientId(ClientModel clientId) {
        this.clientId = clientId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<InvoiceDetailsModel> getInvoiceDetails() {
        return invoiceDetails;
    }

    public void setInvoiceDetails(List<InvoiceDetailsModel> invoiceDetails) {
        this.invoiceDetails = invoiceDetails;
    }

    @Override
    public String toString() {
        return "InvoiceModel{" +
                "id=" + id + ", total=" + total + '}';
    }
}
