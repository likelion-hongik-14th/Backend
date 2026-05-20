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
    public ResponseEntity<CartResponseDto> getCartResponseDto(@RequestParam(name = "user_id") Long user_id) {
        CartResponseDto response = cartService.getCartResponseDto(user_id);
        return ResponseEntity.ok(response);
    }

    // 장바구니 상품 수량 변경
    @PatchMapping("/items/{itemid}")
    public ResponseEntity<CartItemResponseDto> updateCartItemQuantity(
            @PathVariable Long itemid,
            @RequestBody CartItemRequestDto requestDto) {
        CartItemResponseDto response = cartService.updateCartItemQuantity(itemid, requestDto.getQuantity());
        return ResponseEntity.ok(response);
    }

    // 장바구니 상품 삭제
    @DeleteMapping("/items/{itemid}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long itemid) {
        cartService.deleteCartItem(itemid);
        return ResponseEntity.ok().build();
    }
}