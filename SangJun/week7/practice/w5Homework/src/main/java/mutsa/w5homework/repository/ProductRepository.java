package mutsa.w5homework.repository;

import mutsa.w5homework.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
