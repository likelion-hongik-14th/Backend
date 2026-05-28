package com.app.mutsa_shoppingmall.repository;

import com.app.mutsa_shoppingmall.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
