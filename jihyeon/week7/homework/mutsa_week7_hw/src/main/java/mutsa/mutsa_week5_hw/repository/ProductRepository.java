package mutsa.mutsa_week5_hw.repository;

import mutsa.mutsa_week5_hw.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
