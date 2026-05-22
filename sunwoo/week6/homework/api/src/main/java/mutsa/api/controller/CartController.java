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
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    //장바구니 전체 조회
    @GetMapping
    public ResponseEntity<CartResponseDto> getCart() {
        return ResponseEntity.ok(cartService.getAllCart());
    }

    //장바구니에 상품 추가
    @PostMapping("/items")
    public ResponseEntity<Void> addCart(@RequestBody @Valid CartItemRequestDto requestDto) {
        cartService.addCart(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //장바구니에서 상품 수량 변경
    @PatchMapping("/items/{cartItemId}")
    public ResponseEntity<Void> updateCartItemQuantity(@PathVariable Long cartItemId, @RequestBody @Valid CartItemUpdateDto updateDto) {
        cartService.updateCartItemQuantity(cartItemId, updateDto);
        return ResponseEntity.ok().build();
    }

    //장바구니에서 상품 삭제
    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long cartItemId) {
        cartService.deleteCartItem(cartItemId);
        return ResponseEntity.ok().build();
    }

    //장바구니 전체 상품 삭제
    @DeleteMapping
    public ResponseEntity<Void> deleteCart() {
        cartService.initCart();
        return ResponseEntity.ok().build();
    }

}
