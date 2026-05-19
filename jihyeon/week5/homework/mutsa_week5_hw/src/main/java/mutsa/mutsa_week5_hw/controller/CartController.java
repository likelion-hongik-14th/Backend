package mutsa.mutsa_week5_hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.mutsa_week5_hw.dto.CartItemRequestDto;
import mutsa.mutsa_week5_hw.dto.CartItemUpdateDto;
import mutsa.mutsa_week5_hw.dto.CartResponseDto;
import mutsa.mutsa_week5_hw.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts") // 수정: 데이터 요청용 API 및 버전1임을 명시
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 장바구니 조회
    @GetMapping
    public ResponseEntity<CartResponseDto> getCart() {
        return ResponseEntity.ok(cartService.getCart());
    }

    // 장바구니에 상품 추가
    @PostMapping("/items")
    public ResponseEntity<CartResponseDto> addItem(
            @RequestBody @Valid CartItemRequestDto requestDto) { // 수정: DTO에서 유효성 검사를 하도록 @Valid 추가

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cartService.addItem(requestDto));
    }

    // 장바구니 상품 수량 변경
    @PatchMapping("/items/{itemId}")
    public ResponseEntity<CartResponseDto> updateItemQuantity(
            @PathVariable Long itemId,
            @RequestBody @Valid CartItemUpdateDto requestDto) { // 수정: DTO에서 유효성 검사를 하도록 @Valid 추가

        return ResponseEntity.ok(
                cartService.updateItemQuantity(itemId, requestDto)
        );
    }

    // 장바구니 상품 삭제
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long itemId) {
        cartService.deleteItem(itemId);
        return ResponseEntity.noContent().build();
    }
}
