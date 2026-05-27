package org.example.shopping.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer totalPrice;
    private Integer totalQuantity;

    @Builder.Default
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems =  new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Cart(User user){
        this.user = user;
    }

    public CartItem addCartItem(Product product, Integer quantity){
        CartItem existingItem = cartItems.stream()
                .filter(item -> item.getProduct().getId().equals(product.getId())).findFirst().orElse(null);

        if(existingItem == null){
            return CartItem.builder()
                    .cart(this)
                    .product(product)
                    .quantity(quantity)
                    .build();
        } else {
            existingItem.addQuantity(quantity);
            return existingItem;
        }
    }
}
