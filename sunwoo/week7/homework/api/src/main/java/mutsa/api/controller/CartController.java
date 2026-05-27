package mutsa.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.api.dto.CartItemRequestDto;
import mutsa.api.dto.CartItemUpdateDto;
import mutsa.api.dto.CartResponseDto;
import mutsa.api.global.apiPayload.ApiResponse;
import mutsa.api.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{userId}/cart")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "Cart API")
public class CartController {
    private final CartService cartService;

    //장바구니 전체 조회
    @GetMapping
    @Operation(summary = "장바구니 조회", description = "유저의 장바구니 전체 상품을 조회하는 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON2000", description = "장바구니 조회 성공", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_4041", description = "유저를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<CartResponseDto>> getCart(@PathVariable Long userId) {
        CartResponseDto cart = cartService.getAllCart(userId);
        return ResponseEntity.ok(ApiResponse.onSuccess("장바구니 조회 성공", cart));
    }

    //장바구니에 상품 추가
    @PostMapping("/items")
    @Operation(summary = "장바구니 상품 추가", description = "상품을 유저의 장바구니에 추가하는 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON2000", description = "상품이 장바구니에 추가되었습니다", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_4001", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_4041", description = "유저를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT_4041", description = "상품을 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART_4002", description = "장바구니 상품 수량이 재고보다 많습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<Void>> addCart(
            @PathVariable Long userId,
            @RequestBody @Valid CartItemRequestDto requestDto
    ) {
        cartService.addCart(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.onSuccess("상품이 장바구니에 추가되었습니다"));
    }

    //장바구니에서 상품 수량 변경
    @PatchMapping("/items/{cartItemId}")
    @Operation(summary = "장바구니 상품 수량 변경", description = "장바구니에 담긴 상품의 수량을 변경하는 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON2000", description = "장바구니 상품 수량이 변경되었습니다", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_4001", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_4041", description = "유저를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART_4042", description = "장바구니 상품을 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART_4002", description = "장바구니 상품 수량이 재고보다 많습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<Void>> updateCartItemQuantity(
            @PathVariable Long userId,
            @PathVariable Long cartItemId,
            @RequestBody @Valid CartItemUpdateDto updateDto
    ) {
        cartService.updateCartItemQuantity(userId, cartItemId, updateDto);
        return ResponseEntity.ok(ApiResponse.onSuccess("장바구니 상품 수량이 변경되었습니다"));
    }

    //장바구니에서 상품 삭제
    @DeleteMapping("/items/{cartItemId}")
    @Operation(summary = "장바구니 상품 삭제", description = "장바구니에 담긴 특정 상품을 삭제하는 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON2000", description = "장바구니 상품이 삭제되었습니다", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_4041", description = "유저를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART_4042", description = "장바구니 상품을 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<Void>> deleteCartItem(
            @PathVariable Long userId,
            @PathVariable Long cartItemId
    ) {
        cartService.deleteCartItem(userId, cartItemId);
        return ResponseEntity.ok(ApiResponse.onSuccess("장바구니 상품이 삭제되었습니다"));
    }

    //장바구니 전체 상품 삭제
    @DeleteMapping
    @Operation(summary = "장바구니 비우기", description = "유저의 장바구니 전체 상품을 삭제하는 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON2000", description = "장바구니가 비워졌습니다", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_4041", description = "유저를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<Void>> deleteCart(@PathVariable Long userId) {
        cartService.initCart(userId);
        return ResponseEntity.ok(ApiResponse.onSuccess("장바구니가 비워졌습니다"));
    }

}
