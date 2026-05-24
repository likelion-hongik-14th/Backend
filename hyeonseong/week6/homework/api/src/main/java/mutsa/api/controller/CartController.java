package mutsa.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.api.dto.CartItemRequestDto;
import mutsa.api.dto.CartItemUpdateDto;
import mutsa.api.dto.CartResponseDto;
import mutsa.api.global.apiPayload.ApiResponse;
import mutsa.api.global.apiPayload.code.CartSuccessCode;
import mutsa.api.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
@RequiredArgsConstructor
@Tag(name = "Cart API", description = "장바구니 관련 API")
public class CartController {

    private final CartService cartService;

    // 내 장바구니 및 담긴 상품 목록 조회 API
    @GetMapping
    @Operation(summary = "장바구니 조회", description = "로그인한 유저의 장바구니와 담긴 상품 목록을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART200_1", description = "장바구니 조회에 성공했습니다.")
    })
    public ResponseEntity<ApiResponse<CartResponseDto>> getCart(
            @RequestParam Long userId){
        return ResponseEntity.ok(ApiResponse.onSuccess(CartSuccessCode.CART_OK, cartService.getCart(userId)));
    }

    // 장바구니에 새로운 상품 추가 API
    @PostMapping
    @Operation(summary = "장바구니 상품 추가", description = "장바구니에 새로운 상품을 추가합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART201_1", description = "장바구니에 상품이 추가되었습니다.")
    })
    public ResponseEntity<ApiResponse<Void>> addCartItem(
            @RequestParam Long userId,
            @Valid @RequestBody CartItemRequestDto requestDto){
        cartService.addCartItem(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess(CartSuccessCode.CART_ITEM_ADDED));
    }

    // 장바구니에 담긴 특정 상품의 수량 변경 API
    @PatchMapping("/items/{cartItemId}")
    @Operation(summary = "장바구니 수량 변경", description = "장바구니에 담긴 특정 상품의 수량을 변경합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART200_2", description = "장바구니 상품 수량이 변경되었습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FORBIDDEN", description = "본인의 장바구니 아이템이 아닙니다.")
    })
    public ResponseEntity<ApiResponse<Void>> updateCartItemQuantity(
            @RequestParam Long userId,
            @PathVariable Long cartItemId,
            @Valid @RequestBody CartItemUpdateDto updateDto){
        cartService.updateCartItemQuantity(cartItemId, userId, updateDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(CartSuccessCode.CART_ITEM_UPDATED));
    }

    // 장바구니에서 특정 상품만 개별 삭제 API
    @DeleteMapping("/items/{cartItemId}")
    @Operation(summary = "장바구니 상품 삭제", description = "장바구니에서 특정 상품을 개별 삭제합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART200_3", description = "장바구니에서 상품이 삭제되었습니다.")
    })
    public ResponseEntity<ApiResponse<Void>> removeCartItem(
            @RequestParam Long userId,
            @PathVariable Long cartItemId){
        cartService.removeCartItem(cartItemId, userId);
        return ResponseEntity.ok(ApiResponse.onSuccess(CartSuccessCode.CART_ITEM_DELETED));
    }

    // 장바구니 전체 비우기 API
    @DeleteMapping
    @Operation(summary = "장바구니 전체 비우기", description = "유저의 장바구니를 초기화합니다.")
    public ResponseEntity<ApiResponse<Void>> clearCart(
            @RequestParam Long userId){
        cartService.clearCart(userId);
        return ResponseEntity.ok(ApiResponse.onSuccess(CartSuccessCode.CART_CLEARED));
    }
}