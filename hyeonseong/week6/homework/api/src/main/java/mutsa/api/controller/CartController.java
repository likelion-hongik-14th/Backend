package mutsa.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.api.dto.CartItemRequestDto;
import mutsa.api.dto.CartItemUpdateDto;
import mutsa.api.dto.CartResponseDto;
import mutsa.api.global.apiPayload.ApiResponse;
import mutsa.api.global.apiPayload.code.CartSuccessCode;
import mutsa.api.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 내 장바구니 및 담긴 상품 목록 조회 API
    @GetMapping
    public ResponseEntity<ApiResponse<CartResponseDto>> getCart(
            @RequestParam Long userId){
        return ResponseEntity.ok(ApiResponse.onSuccess(CartSuccessCode.CART_OK, cartService.getCart(userId)));
    }

    // 장바구니에 새로운 상품 추가 API
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> addCartItem(
            @RequestParam Long userId,
            @Valid @RequestBody CartItemRequestDto requestDto){
        cartService.addCartItem(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess(CartSuccessCode.CART_ITEM_ADDED));
    }

    // 장바구니에 담긴 특정 상품의 수량 변경 API
    @PatchMapping("/items/{cartItemId}")
    public ResponseEntity<ApiResponse<Void>> updateCartItemQuantity(
            @RequestParam Long userId,
            @PathVariable Long cartItemId,
            @Valid @RequestBody CartItemUpdateDto updateDto){
        cartService.updateCartItemQuantity(cartItemId, userId, updateDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(CartSuccessCode.CART_ITEM_UPDATED));
    }

    // 장바구니에서 특정 상품만 개별 삭제 API
    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<ApiResponse<Void>> removeCartItem(
            @RequestParam Long userId,
            @PathVariable Long cartItemId){
        cartService.removeCartItem(cartItemId, userId);
        return ResponseEntity.ok(ApiResponse.onSuccess(CartSuccessCode.CART_ITEM_DELETED));
    }

    // 장바구니 전체 비우기 API
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> clearCart(
            @RequestParam Long userId){
        cartService.clearCart(userId);
        return ResponseEntity.ok(ApiResponse.onSuccess(CartSuccessCode.CART_CLEARED));
    }
}