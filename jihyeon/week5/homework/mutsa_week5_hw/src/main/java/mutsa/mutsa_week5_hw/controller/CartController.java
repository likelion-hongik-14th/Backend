package mutsa.mutsa_week5_hw.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.mutsa_week5_hw.dto.CartItemRequestDto;
import mutsa.mutsa_week5_hw.dto.CartItemUpdateDto;
import mutsa.mutsa_week5_hw.dto.CartResponseDto;
import mutsa.mutsa_week5_hw.global.ApiResponse;
import mutsa.mutsa_week5_hw.service.CartService;
import mutsa.mutsa_week5_hw.service.ProductService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts") // 수정: 데이터 요청용 API 및 버전1임을 명시
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ProductService productService;

    // 장바구니 조회 -> ApiResponse로 래핑
    @GetMapping
    public ApiResponse<CartResponseDto> getCart() {
        CartResponseDto response = cartService.getCart();
        return ApiResponse.onSuccess("장바구니 조회에 성공했습니다.", response);
    }

    // 장바구니에 상품 추가 -> ApiResponse로 래핑
    @PostMapping("/items")
    public ApiResponse<CartResponseDto> addItem(
            @RequestBody @Valid CartItemRequestDto requestDto) { // 수정: DTO에서 유효성 검사를 하도록 @Valid 추가

        CartResponseDto response = cartService.addItem(requestDto);
        return ApiResponse.onSuccess("장바구니 상품 추가에 성공했습니다.", response);
    }

    // 장바구니 상품 수량 변경 -> ApiResponse로 래핑
    @PatchMapping("/items/{itemId}")
    public ApiResponse<CartResponseDto> updateItemQuantity(
            @PathVariable Long itemId,
            @RequestBody @Valid CartItemUpdateDto requestDto) { // 수정: DTO에서 유효성 검사를 하도록 @Valid 추가

        CartResponseDto response = cartService.updateItemQuantity(itemId, requestDto);
        return ApiResponse.onSuccess("장바구니 상품 수량 변경에 성공했습니다.", response);
    }

    // 장바구니 상품 삭제 -> ApiResponse로 래핑
    @DeleteMapping("/items/{itemId}")
    public ApiResponse<Void> deleteItem(@PathVariable Long itemId) {

        cartService.deleteItem(itemId);
        return ApiResponse.onSuccess("장바구니 상품 삭제에 성공했습니다.", null);
    }
}
