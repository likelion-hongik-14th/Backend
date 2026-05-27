package com.app.mutsa_shoppingmall.repository;

import com.app.mutsa_shoppingmall.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
