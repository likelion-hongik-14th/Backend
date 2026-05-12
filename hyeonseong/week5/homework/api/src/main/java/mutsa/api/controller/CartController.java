package mutsa.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.api.dto.CartItemRequestDto;
import mutsa.api.dto.CartItemUpdateDto;
import mutsa.api.dto.CartResponseDto;
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
    public ResponseEntity<CartResponseDto> getCart(){
        return ResponseEntity.ok(cartService.getCart());
    }

    // 장바구니에 새로운 상품 추가 API
    @PostMapping
    public ResponseEntity<Void> addCartItem(@Valid @RequestBody CartItemRequestDto requestDto){
        cartService.addCartItem(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 장바구니에 담긴 특정 상품의 수량 변경 API
    @PatchMapping("/items/{cartItemId}")
    public ResponseEntity<Void> updateCartItemQuantity(
            @PathVariable Long cartItemId,
            @Valid @RequestBody CartItemUpdateDto updateDto){
        cartService.updateCartItemQuantity(cartItemId, updateDto);
        return ResponseEntity.noContent().build();
    }

    // 장바구니에서 특정 상품만 개별 삭제 API
    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long cartItemId){
        cartService.removeCartItem(cartItemId);
        return ResponseEntity.noContent().build();
    }

    // 장바구니 전체 비우기 API
    @DeleteMapping
    public ResponseEntity<Void> clearCart(){
        cartService.clearCart();
        return ResponseEntity.noContent().build();
    }
}