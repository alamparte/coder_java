package final_invoice.jpa.repository;

import final_invoice.jpa.model.InvoiceDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceDetailsRepository extends JpaRepository<InvoiceDetailsModel, Integer> {
}
