package final_invoice.jpa.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
//@Data
public class InvoiceDTO {

    private Integer id;
    private int clientId;
    private String clientName;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @JsonManagedReference
    private List<DetailsDTO> products;
    private double total;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public List<DetailsDTO> getProducts() {
        return products;
    }

    public void setProducts(List<DetailsDTO> products) {
        this.products = products;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
