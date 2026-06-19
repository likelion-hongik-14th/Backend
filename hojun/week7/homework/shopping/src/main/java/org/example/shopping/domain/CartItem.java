package org.example.shopping.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.shopping.global.apiPayload.code.domain.CartErrorCode;
import org.example.shopping.global.apiPayload.code.domain.CartItemErrorCode;
import org.example.shopping.global.apiPayload.exception.ProjectException;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_Id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;
    private String description;


    public void updateQuantity(Integer quantity){
        if(quantity <= 0){
            throw new ProjectException(CartItemErrorCode.INVALID_CART_ITEM_QUANTITY);
        }
        this.quantity = quantity;
    }

    public void addQuantity(Integer quantity) {
        this.quantity += quantity;
    }

    public Integer getTotalPrice(){
        return this.quantity * this.product.getPrice();
    }

}
