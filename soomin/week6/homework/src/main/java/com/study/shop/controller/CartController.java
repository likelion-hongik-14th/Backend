package com.study.shop.controller;

import com.study.shop.dto.cart.AddCartItemRequest;
import com.study.shop.dto.cart.CartItemResponse;
import com.study.shop.dto.cart.CartResponse;
import com.study.shop.dto.cart.UpdateCartItemRequest;
import com.study.shop.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartResponse> getCart(@RequestParam Long memberId) {
        return ResponseEntity.ok(cartService.getCart(memberId));
    }

    @PostMapping("/items")
    public ResponseEntity<CartItemResponse> addCartItem(
            @RequestParam Long memberId,
            @Valid @RequestBody AddCartItemRequest request
            ) {
        CartItemResponse response = cartService.addCartItem(memberId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/items/{cartItemId}")
    public ResponseEntity<CartItemResponse> updateCartItem(
            @RequestParam Long memberId,
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateCartItemRequest request
            ) {
        return ResponseEntity.ok(cartService.updateCartItem(memberId, cartItemId, request));
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(
            @RequestParam Long memberId,
            @PathVariable Long cartItemId
    ) {
        cartService.deleteCartItem(memberId, cartItemId);
        return ResponseEntity.noContent().build();
    }

}
