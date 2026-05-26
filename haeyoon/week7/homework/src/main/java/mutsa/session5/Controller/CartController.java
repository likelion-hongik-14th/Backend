package mutsa.session5.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.session5.Dto.CartItemRequestDto;
import mutsa.session5.Dto.CartItemResponseDto;
import mutsa.session5.Dto.CartResponseDto;
import mutsa.session5.Service.CartService;
import mutsa.session5.global.apipayload.ApiResponse;
import mutsa.session5.global.apipayload.exception.code.CartSuccessCode;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    // 장바구니 상품 담기
    @PostMapping("/items")
    public ApiResponse<CartItemResponseDto> addCartItem(@Valid @RequestBody CartItemRequestDto requestDto) {
        CartItemResponseDto response = cartService.addCartItem(requestDto);
        return ApiResponse.onSuccess(CartSuccessCode.ADD_ITEM_SUCCESS.getMessage(), response);
    }

    // 장바구니 상품 조회
    @GetMapping
    public ApiResponse<CartResponseDto> getCartResponseDto(@RequestParam Long userId) {
        CartResponseDto response = cartService.getCartResponseDto(userId);
        return ApiResponse.onSuccess(CartSuccessCode.GET_CART_SUCCESS.getMessage(), response);
    }

    // 장바구니 상품 수량 변경
    @PatchMapping("/items/{itemId}")
    public ApiResponse<CartItemResponseDto> updateCartItemQuantity(
            @PathVariable Long itemId,
            @RequestBody CartItemRequestDto requestDto) {
        CartItemResponseDto response = cartService.updateCartItemQuantity(itemId, requestDto.getQuantity());
        return ApiResponse.onSuccess(CartSuccessCode.UPDATE_QUANTITY_SUCCESS.getMessage(), response);
    }

    // 장바구니 상품 삭제
    @DeleteMapping("/items/{itemId}")
    public ApiResponse<Void> deleteCartItem(@PathVariable Long itemId) {
        cartService.deleteCartItem(itemId);
        return ApiResponse.onSuccess(CartSuccessCode.DELETE_ITEM_SUCCESS.getMessage());
    }
}