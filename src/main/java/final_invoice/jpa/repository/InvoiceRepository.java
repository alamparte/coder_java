package final_invoice.jpa.repository;

import final_invoice.jpa.model.InvoiceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<InvoiceModel, Integer> {
}
