package mutsa.shop.repository;

import mutsa.shop.domain.Cart;
import mutsa.shop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
