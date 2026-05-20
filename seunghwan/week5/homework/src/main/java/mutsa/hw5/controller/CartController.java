package mutsa.hw5.controller;

import lombok.RequiredArgsConstructor;
import mutsa.hw5.dto.cart.CartResponseDto;
import mutsa.hw5.dto.cartitem.CartItemRequestDto;
import mutsa.hw5.dto.cartitem.CartItemResponseDto;
import mutsa.hw5.dto.cartitem.CartItemUpdateDto;
import mutsa.hw5.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    // 장바구니 조회
    @GetMapping
    public ResponseEntity<CartResponseDto> getCart(@RequestParam Long memberId) {
        return ResponseEntity.ok(cartService.getCart(memberId));
    }

    // 장바구니 상품 담기
    @PostMapping("/items")
    public ResponseEntity<CartItemResponseDto> addCartItem(
            @RequestParam Long memberId,
            @Valid @RequestBody CartItemRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addCartItem(memberId, dto));
    }

    // 장바구니 수량 변경
    @PatchMapping("/items/{itemId}")
    public ResponseEntity<CartItemResponseDto> updateCartItem(
            @PathVariable Long itemId,
            @Valid @RequestBody CartItemUpdateDto dto) {
        return ResponseEntity.ok(cartService.updateCartItem(itemId, dto));
    }

    // 장바구니 상품 삭제
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long itemId) {
        cartService.deleteCartItem(itemId);
        return ResponseEntity.noContent().build();
    }
}