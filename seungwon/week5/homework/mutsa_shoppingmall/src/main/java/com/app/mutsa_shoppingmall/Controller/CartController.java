package com.app.mutsa_shoppingmall.Controller;

import com.app.mutsa_shoppingmall.DTO.CartDto;
import com.app.mutsa_shoppingmall.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartDto.CartResponse> getCart() {
        return ResponseEntity.ok(cartService.getCart());
    }

    @PostMapping("/items")
    public ResponseEntity<CartDto.ItemResponse> addCartItem(@RequestBody CartDto.ItemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cartService.addCartItem(request));
    }

    @PatchMapping("/items/{itemId}")
    public ResponseEntity<CartDto.ItemResponse> updateCartItem(
            @PathVariable Long itemId,
            @RequestBody CartDto.ItemRequest request) {
        return ResponseEntity.ok(cartService.updateCartItem(itemId, request));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long itemId) {
        cartService.deleteCartItem(itemId);
        return ResponseEntity.noContent().build();
    }
}