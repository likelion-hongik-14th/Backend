package com.app.mutsa_shoppingmall.controller;

import com.app.mutsa_shoppingmall.dto.ApiResponse;
import com.app.mutsa_shoppingmall.dto.CartDto;
import com.app.mutsa_shoppingmall.service.CartService;
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
    public ResponseEntity<ApiResponse<CartDto.CartResponse>> getCart() {
        return ResponseEntity.ok(ApiResponse.onSuccess("장바구니 조회 성공", cartService.getCart()));
    }

    @PostMapping("/items")
    public ResponseEntity<ApiResponse<CartDto.ItemResponse>> addCartItem(@RequestBody CartDto.ItemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess("장바구니 상품 추가 성공", cartService.addCartItem(request)));
    }

    @PatchMapping("/items/{itemId}")
    public ResponseEntity<ApiResponse<CartDto.ItemResponse>> updateCartItem(
            @PathVariable Long itemId,
            @RequestBody CartDto.ItemRequest request) {
        return ResponseEntity.ok(ApiResponse.onSuccess("장바구니 상품 수정 성공", cartService.updateCartItem(itemId, request)));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<ApiResponse<Void>> deleteCartItem(@PathVariable Long itemId) {
        cartService.deleteCartItem(itemId);
        return ResponseEntity.ok(ApiResponse.onSuccess("장바구니 상품 삭제 성공"));
    }
}