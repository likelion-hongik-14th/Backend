package mutsa.shop.controller;

import lombok.RequiredArgsConstructor;
import mutsa.shop.dto.cartItemDto.CartItemAddRequestDto;
import mutsa.shop.dto.cartItemDto.CartItemResponseDto;
import mutsa.shop.dto.cartItemDto.CartItemUpdateRequestDto;
import mutsa.shop.dto.CartResponseDto;
import mutsa.shop.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final Long TEMP_MEMBER_ID = 1L;

    //장바구니 상품 추가
    @PostMapping("/items")
    public ResponseEntity<CartItemResponseDto> addItem(@RequestBody CartItemAddRequestDto requestDto) {
        CartItemResponseDto responseDto = cartService.addItem(requestDto, TEMP_MEMBER_ID);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    //장바구니 조회
    @GetMapping
    public ResponseEntity<CartResponseDto> getCart() {
        CartResponseDto responseDto = cartService.getCart(TEMP_MEMBER_ID);
        return ResponseEntity.ok(responseDto);
    }

    //장바구니 상품 수량 변경
    @PatchMapping("/items/{itemId}")
    public ResponseEntity<CartItemResponseDto> updateQuantity(
            @PathVariable Long itemId,
            @RequestBody CartItemUpdateRequestDto requestDto) {
        CartItemResponseDto responseDto = cartService.updateItemQuantity(itemId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    //장바구니 상품 삭제
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        cartService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }
}
