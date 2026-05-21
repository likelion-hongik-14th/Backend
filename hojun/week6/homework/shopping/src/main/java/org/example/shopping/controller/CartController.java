package org.example.shopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.shopping.dto.cart.CartItemRequestDto;
import org.example.shopping.dto.cart.CartItemUpdateDto;
import org.example.shopping.dto.cart.CartResponseDto;
import org.example.shopping.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/{userId}/items")
    public ResponseEntity<Void> addCartItem(@PathVariable Long userId, @RequestBody CartItemRequestDto request){

        cartService.addCartItem(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponseDto> getCart(@PathVariable Long userId){

        CartResponseDto cart =  cartService.getCart(userId);
        return  ResponseEntity.ok(cart);
    }

    @PatchMapping("/{userId}/items/{cartItemId}")
    public ResponseEntity<Void> updateCartItem(@PathVariable Long userId, @PathVariable Long cartItemId, @RequestBody CartItemUpdateDto request){
        cartService.updateCartItem(userId, cartItemId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/items/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long userId, @PathVariable Long cartItemId){
        cartService.deleteCartItem(userId, cartItemId);
        return ResponseEntity.noContent().build();
    }
}
