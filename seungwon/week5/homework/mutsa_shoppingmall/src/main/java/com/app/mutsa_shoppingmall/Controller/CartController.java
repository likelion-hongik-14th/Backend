package com.app.mutsa_shoppingmall.Controller;

import com.app.mutsa_shoppingmall.DTO.CartDto;
import com.app.mutsa_shoppingmall.Service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    // 장바구니 조회
    @GetMapping
    public ResponseEntity<CartDto.CartResponse> getCart() {
        return ResponseEntity.ok(cartService.getCart());
    }

    // 장바구니 상품 추가
    @PostMapping("/items")
    public ResponseEntity<CartDto.ItemResponse> addCartItem(@RequestBody CartDto.ItemRequest request) {
        return ResponseEntity.ok(cartService.addCartItem(request));
    }

    // 장바구니 상품 수량/옵션 변경
    @PatchMapping("/items/{itemId}")
    public ResponseEntity<CartDto.ItemResponse> updateCartItem(
            @PathVariable Long itemId,
            @RequestBody CartDto.ItemRequest request) {
        return ResponseEntity.ok(cartService.updateCartItem(itemId, request));
    }

    // 장바구니 상품 삭제
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long itemId) {
        cartService.deleteCartItem(itemId);
        return ResponseEntity.ok().build();
    }
}
