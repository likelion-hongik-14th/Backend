package mutsa.homework.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.homework.dto.cart.AddCartItemRequestDto;
import mutsa.homework.dto.cart.CartItemResponseDto;
import mutsa.homework.dto.cart.CartResponseDto;
import mutsa.homework.dto.cart.UpdateQuantityRequestDto;
import mutsa.homework.global.dto.ApiResponse;
import mutsa.homework.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<ApiResponse<CartItemResponseDto>> createCartItem(
            @RequestHeader("X-User-Id") Long userId,
            @Valid
            @RequestBody AddCartItemRequestDto requestDto
    ) {

        CartItemResponseDto responseDto = cartService.addProductToCart(userId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(responseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CartResponseDto>> getCartItems(
            @RequestHeader("X-User-Id") Long userId
    ) {

        CartResponseDto responseDto = cartService.getCartItems(userId);

        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @PatchMapping("/items/{itemId}")
    public  ResponseEntity<ApiResponse<CartItemResponseDto>> patchCartItem(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable("itemId") Long itemId,
            @RequestBody UpdateQuantityRequestDto requestDto
    ) {
        CartItemResponseDto responseDto = cartService.updateCartItemQuantity(userId, itemId, requestDto);

        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<ApiResponse<Void>> deleteCartItem(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable("itemId") Long itemId
    ) {
        cartService.deleteCartItem(userId, itemId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
