package com.app.mutsa_shoppingmall.Controller;

import com.app.mutsa_shoppingmall.DTO.CartDto;
import com.app.mutsa_shoppingmall.Service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Tag(name = "Cart", description = "장바구니 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    @Operation(summary = "장바구니 조회", description = "현재 장바구니 목록과 총 금액을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    @GetMapping
    public ResponseEntity<CartDto.CartResponse> getCart() {
        return ResponseEntity.ok(cartService.getCart());
    }

    @Operation(summary = "장바구니 상품 추가", description = "장바구니에 상품을 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "추가 성공"),
            @ApiResponse(responseCode = "404", description = "PRODUCT_4041 - 상품을 찾을 수 없습니다.")
    })
    @PostMapping("/items")
    public ResponseEntity<CartDto.ItemResponse> addCartItem(@Valid @RequestBody CartDto.ItemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(cartService.addCartItem(request));
    }

    @Operation(summary = "장바구니 상품 수정", description = "장바구니 아이템의 수량 및 색상을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "404", description = "CART_4042 - 장바구니 아이템이 존재하지 않습니다.")
    })
    @PatchMapping("/items/{itemId}")
    public ResponseEntity<CartDto.ItemResponse> updateCartItem(
            @PathVariable Long itemId,
            @Valid @RequestBody CartDto.ItemRequest request) {
        return ResponseEntity.ok(cartService.updateCartItem(itemId, request));
    }

    @Operation(summary = "장바구니 상품 삭제", description = "장바구니에서 특정 아이템을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "삭제 성공")
    })
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long itemId) {
        cartService.deleteCartItem(itemId);
        return ResponseEntity.noContent().build();
    }
}