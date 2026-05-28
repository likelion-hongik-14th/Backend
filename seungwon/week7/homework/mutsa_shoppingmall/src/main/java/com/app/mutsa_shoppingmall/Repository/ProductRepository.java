package com.app.mutsa_shoppingmall.Repository;

import com.app.mutsa_shoppingmall.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
