package mutsa.homework.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.homework.controller.docs.CartApiDocs;
import mutsa.homework.dto.cart.AddCartItemRequestDto;
import mutsa.homework.dto.cart.CartItemResponseDto;
import mutsa.homework.dto.cart.CartResponseDto;
import mutsa.homework.dto.cart.UpdateQuantityRequestDto;
import mutsa.homework.global.apiPayload.GlobalResponse;
import mutsa.homework.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController implements CartApiDocs {

    private final CartService cartService;

    @Override
    @PostMapping("/items")
    public ResponseEntity<GlobalResponse<CartItemResponseDto>> createCartItem(
            @RequestHeader("X-User-Id") Long userId,
            @Valid
            @RequestBody AddCartItemRequestDto requestDto
    ) {

        CartItemResponseDto responseDto = cartService.addProductToCart(userId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GlobalResponse.onSuccessCreate(responseDto));
    }

    @Override
    @GetMapping
    public ResponseEntity<GlobalResponse<CartResponseDto>> getCartItems(
            @RequestHeader("X-User-Id") Long userId
    ) {

        CartResponseDto responseDto = cartService.getCartItems(userId);

        return ResponseEntity.ok(GlobalResponse.onSuccess(responseDto));
    }

    @Override
    @PatchMapping("/items/{itemId}")
    public  ResponseEntity<GlobalResponse<CartItemResponseDto>> patchCartItem(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable("itemId") Long itemId,
            @Valid
            @RequestBody UpdateQuantityRequestDto requestDto
    ) {
        CartItemResponseDto responseDto = cartService.updateCartItemQuantity(userId, itemId, requestDto);

        return ResponseEntity.ok(GlobalResponse.onSuccess(responseDto));
    }

    @Override
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<GlobalResponse<Void>> deleteCartItem(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable("itemId") Long itemId
    ) {
        cartService.deleteCartItem(userId, itemId);

        return ResponseEntity.ok(GlobalResponse.onSuccess());
    }
}
