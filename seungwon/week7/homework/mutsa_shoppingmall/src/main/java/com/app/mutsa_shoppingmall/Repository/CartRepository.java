package com.app.mutsa_shoppingmall.Repository;

import com.app.mutsa_shoppingmall.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
