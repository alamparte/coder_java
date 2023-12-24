package final_invoice.jpa.repository;

import final_invoice.jpa.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductModel, Integer> {
    Optional<ProductModel> findByCode(String code);
    Optional<ProductModel> findById(Integer id);
}
