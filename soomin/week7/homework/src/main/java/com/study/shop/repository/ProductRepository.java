package com.study.shop.repository;

import com.study.shop.domain.Product;
import com.study.shop.domain.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByStatusNot(ProductStatus status);

}
