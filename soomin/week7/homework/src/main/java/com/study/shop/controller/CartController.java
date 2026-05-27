package com.study.shop.controller;

import com.study.shop.dto.cart.AddCartItemRequest;
import com.study.shop.dto.cart.CartItemResponse;
import com.study.shop.dto.cart.CartResponse;
import com.study.shop.dto.cart.UpdateCartItemRequest;
import com.study.shop.global.apiPayload.ApiResponse;
import com.study.shop.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "장바구니 API")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "장바구니 조회", description = "memberId를 기준으로 회원의 장바구니를 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "장바구니 조회 성공",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "MEMBER404_1 또는 CART404_1",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<ApiResponse<CartResponse>> getCart(@RequestParam Long memberId) {
        CartResponse response = cartService.getCart(memberId);

        return ResponseEntity
                .ok(ApiResponse.onSuccess("장바구니 조회 성공" , response));
    }

    @Operation(summary = "장바구니 상품 추가", description = "상품과 수량을 입력받아 장바구니에 상품을 추가합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "장바구니 상품 추가 성공",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "CART404_1 또는 PRODUCT404_1",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "PRODUCT400_2 - 상품 재고가 부족합니다.",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/items")
    public ResponseEntity<ApiResponse<CartItemResponse>> addCartItem(
            @RequestParam Long memberId,
            @Valid @RequestBody AddCartItemRequest request
            ) {
        CartItemResponse response = cartService.addCartItem(memberId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess("장바구니 상품 추가 성공" , response));
    }

    @Operation(summary = "장바구니 상품 수량 변경", description = "장바구니 상품의 수량을 변경합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "장바구니 상품 수량 변경 성공",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "CART403_1 - 다른 회원의 장바구니에는 접근할 수 없습니다.",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "CART404_1 또는 CART404_2",
                    content = @Content(mediaType = "application/json"))
    })
    @PatchMapping("/items/{cartItemId}")
    public ResponseEntity<ApiResponse<CartItemResponse>> updateCartItem(
            @RequestParam Long memberId,
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateCartItemRequest request
            ) {
        CartItemResponse response = cartService.updateCartItem(memberId, cartItemId, request);

        return ResponseEntity
                .ok(ApiResponse.onSuccess("장바구니 상품 수량 변경 성공" , response));
    }

    @Operation(summary = "장바구니 상품 삭제", description = "장바구니에서 특정 상품을 삭제합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "장바구니 상품 삭제 성공",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "403",
                    description = "CART403_1 - 다른 회원의 장바구니에는 접근할 수 없습니다.",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "CART404_1 또는 CART404_2",
                    content = @Content(mediaType = "application/json"))
    })
    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<ApiResponse<Void>> deleteCartItem(
            @RequestParam Long memberId,
            @PathVariable Long cartItemId
    ) {
        cartService.deleteCartItem(memberId, cartItemId);

        return ResponseEntity
                .ok(ApiResponse.onSuccess("장바구니 상품 삭제 성공"));
    }

}
