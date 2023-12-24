package final_invoice.jpa.repository;

import final_invoice.jpa.model.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientModel, Integer> {
    Optional<ClientModel> findByDocNumber(String doc);
}