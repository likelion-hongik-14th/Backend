package com.app.mutsa_shoppingmall.Repository;

import com.app.mutsa_shoppingmall.Entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
