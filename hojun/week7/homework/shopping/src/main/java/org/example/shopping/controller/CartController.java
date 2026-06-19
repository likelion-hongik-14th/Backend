package org.example.shopping.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.shopping.dto.cart.*;
import org.example.shopping.global.apiPayload.ApiResponse;
import org.example.shopping.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Cart API", description = "장바구니 도메인 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/carts")
public class CartController {
    private final CartService cartService;

    @Operation(summary = "장바구니 물품 추가", description = "상품 아이디와 수량을 받아서 장바구니에 추가")
    @PostMapping("/{userId}/items")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CREATED", description = "장바구니에 상품이 추가되었습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "OUT_OF_STOCK", description = "상품의 재고가 부족합니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_NOT_FOUND", description = "존재하지 않는 회원입니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT_NOT_FOUND", description = "존재하지 않는 상품입니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART_NOT_FOUND", description = "장바구니 정보가 존재하지 않습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<CartItemResponseDto>> addCartItem(@PathVariable Long userId, @RequestBody @Validated CartItemRequestDto request){
        CartItemResponseDto response = cartService.addCartItem(userId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess("장바구니에 상품이 추가되었습니다.", response));
    }

    @Operation(summary = "장바구니 물품 조회")
    @GetMapping("/{userId}")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SUCCESS", description = "장바구니 조회가 완료되었습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_NOT_FOUND", description = "존재하지 않는 회원입니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART_NOT_FOUND", description = "해당 회원의 장바구니가 존재하지 않습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<CartResponseDto>> getCart(@PathVariable Long userId){
        CartResponseDto cart = cartService.getCart(userId);
        return ResponseEntity.ok(ApiResponse.onSuccess("장바구니 조회가 완료되었습니다.", cart));
    }

    @Operation(summary = "장바구니 상품 수량 수정", description = "변경 원하는 수량으로 업데이트")
    @PatchMapping("/{userId}/items/{cartItemId}")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SUCCESS", description = "장바구니 상품 수량이 수정되었습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "INVALID_QUANTITY", description = "변경 수량이 올바르지 않습니다. (최소 1개 이상)", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_NOT_FOUND", description = "존재하지 않는 회원입니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART_ITEM_NOT_FOUND", description = "해당 장바구니 상품 아이템이 존재하지 않습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<Void>> updateCartItem(@PathVariable Long userId, @PathVariable Long cartItemId, @RequestBody @Validated CartItemUpdateDto request){
        cartService.updateCartItem(userId, cartItemId, request);
        return ResponseEntity.ok(ApiResponse.onSuccess("장바구니 상품 수량이 수정되었습니다."));
    }

    @Operation(summary = "장바구니 상품 삭제")
    @DeleteMapping("/{userId}/items/{cartItemId}")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SUCCESS", description = "장바구니 상품이 삭제되었습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_NOT_FOUND", description = "존재하지 않는 회원입니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART_ITEM_NOT_FOUND", description = "해당 장바구니 상품 아이템이 존재하지 않습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<CartItemDeleteResponseDto>> deleteCartItem(@PathVariable Long userId, @PathVariable Long cartItemId){
        CartItemDeleteResponseDto response = cartService.deleteCartItem(userId, cartItemId);
        return ResponseEntity.ok(ApiResponse.onSuccess("장바구니 상품이 삭제되었습니다.", response));
    }
}