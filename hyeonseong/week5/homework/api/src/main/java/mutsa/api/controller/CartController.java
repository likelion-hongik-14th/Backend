package mutsa.api.controller;

import lombok.RequiredArgsConstructor;
import mutsa.api.dto.CartItemRequestDto;
import mutsa.api.dto.CartItemUpdateDto;
import mutsa.api.dto.CartResponseDto;
import mutsa.api.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 장바구니 상품 목록 조회 API
    @GetMapping
    public ResponseEntity<CartResponseDto> getCart(){
        return ResponseEntity.ok(cartService.getCart());
    }

    // 장바구니에 상품 추가 API
    @PostMapping
    public ResponseEntity<Void> addCartItem(@RequestBody CartItemRequestDto requestDto){
        cartService.addCartItem(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 장바구니 상품의 수량 변경 API
    @PatchMapping("/items/{cartItemId}")
    public ResponseEntity<Void> updateCartItemQuantity(
            @PathVariable Long cartItemId,
            @RequestBody CartItemUpdateDto updateDto){
        cartService.updateCartItemQuantity(cartItemId, updateDto);
        return ResponseEntity.ok().build();
    }

    // 장바구니의 특정 상품 삭제 API
    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> removeCartItem(@PathVariable Long cartItemId){
        cartService.removeCartItem(cartItemId);
        return ResponseEntity.ok().build();
    }

    // 장바구니 전체 비우기 API
    @DeleteMapping
    public ResponseEntity<Void> clearCart(){
        cartService.clearCart();
        return ResponseEntity.ok().build();
    }
}
