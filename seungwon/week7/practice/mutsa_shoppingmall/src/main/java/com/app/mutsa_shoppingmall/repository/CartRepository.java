package com.app.mutsa_shoppingmall.repository;

import com.app.mutsa_shoppingmall.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
