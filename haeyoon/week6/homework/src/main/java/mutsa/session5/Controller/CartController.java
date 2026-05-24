package mutsa.session5.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.session5.Dto.CartItemRequestDto;
import mutsa.session5.Dto.CartItemResponseDto;
import mutsa.session5.Dto.CartResponseDto;
import mutsa.session5.Service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    // 장바구니 상품 담기
    @PostMapping("/items")
    public ResponseEntity<CartItemResponseDto> addCartItem(@Valid @RequestBody CartItemRequestDto requestDto) {
        CartItemResponseDto response = cartService.addCartItem(requestDto);
        return ResponseEntity.ok(response);
    }

    // 장바구니 상품 조회
    @GetMapping
    public ResponseEntity<CartResponseDto> getCartResponseDto(@RequestParam Long userId) {
        CartResponseDto response = cartService.getCartResponseDto(userId);
        return ResponseEntity.ok(response);
    }

    // 장바구니 상품 수량 변경
    @PatchMapping("/items/{itemId}")
    public ResponseEntity<CartItemResponseDto> updateCartItemQuantity(
            @PathVariable Long itemId,
            @RequestBody CartItemRequestDto requestDto) {
        CartItemResponseDto response = cartService.updateCartItemQuantity(itemId, requestDto.getQuantity());
        return ResponseEntity.ok(response);
    }

    // 장바구니 상품 삭제
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long itemId) {
        cartService.deleteCartItem(itemId);
        return ResponseEntity.ok().build();
    }
}