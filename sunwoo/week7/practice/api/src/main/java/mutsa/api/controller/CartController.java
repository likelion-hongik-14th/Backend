package mutsa.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.api.dto.CartItemRequestDto;
import mutsa.api.dto.CartItemUpdateDto;
import mutsa.api.dto.CartResponseDto;
import mutsa.api.global.apiPayload.ApiResponse;
import mutsa.api.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{userId}/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    //장바구니 전체 조회
    @GetMapping
    public ResponseEntity<ApiResponse<CartResponseDto>> getCart(@PathVariable Long userId) {
        CartResponseDto cart = cartService.getAllCart(userId);
        return ResponseEntity.ok(ApiResponse.onSuccess("장바구니 조회 성공", cart));
    }

    //장바구니에 상품 추가
    @PostMapping("/items")
    public ResponseEntity<ApiResponse<Void>> addCart(
            @PathVariable Long userId,
            @RequestBody @Valid CartItemRequestDto requestDto
    ) {
        cartService.addCart(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.onSuccess("상품이 장바구니에 추가되었습니다"));
    }

    //장바구니에서 상품 수량 변경
    @PatchMapping("/items/{cartItemId}")
    public ResponseEntity<ApiResponse<Void>> updateCartItemQuantity(
            @PathVariable Long userId,
            @PathVariable Long cartItemId,
            @RequestBody @Valid CartItemUpdateDto updateDto
    ) {
        cartService.updateCartItemQuantity(userId, cartItemId, updateDto);
        return ResponseEntity.ok(ApiResponse.onSuccess("장바구니 상품 수량이 변경되었습니다"));
    }

    //장바구니에서 상품 삭제
    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<ApiResponse<Void>> deleteCartItem(
            @PathVariable Long userId,
            @PathVariable Long cartItemId
    ) {
        cartService.deleteCartItem(userId, cartItemId);
        return ResponseEntity.ok(ApiResponse.onSuccess("장바구니 상품이 삭제되었습니다"));
    }

    //장바구니 전체 상품 삭제
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteCart(@PathVariable Long userId) {
        cartService.initCart(userId);
        return ResponseEntity.ok(ApiResponse.onSuccess("장바구니가 비워졌습니다"));
    }

}
