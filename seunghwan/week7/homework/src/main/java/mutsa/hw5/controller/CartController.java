package mutsa.hw5.controller;

import lombok.RequiredArgsConstructor;
import mutsa.hw5.dto.cart.CartResponseDto;
import mutsa.hw5.dto.cartitem.CartItemRequestDto;
import mutsa.hw5.dto.cartitem.CartItemResponseDto;
import mutsa.hw5.dto.cartitem.CartItemUpdateDto;
import mutsa.hw5.global.apiPayload.ApiResponse;
import mutsa.hw5.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
@Tag(name = "Cart", description = "장바구니 관련 API")
public class CartController {

    private final CartService cartService;

    // 장바구니 조회
    @Operation(summary = "장바구니 조회", description = "회원의 장바구니를 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "장바구니 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART404_1", description = "장바구니를 찾을 수 없습니다.")
    })
    @GetMapping
    public ResponseEntity<ApiResponse<CartResponseDto>> getCart(@RequestParam Long memberId) {
        return ResponseEntity.ok(ApiResponse.onSuccess("장바구니 조회 성공", cartService.getCart(memberId)));
    }

    // 장바구니 상품 담기
    @Operation(summary = "장바구니 상품 담기", description = "장바구니에 상품을 추가합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "장바구니 상품 추가 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART404_1", description = "장바구니를 찾을 수 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT404_1", description = "상품을 찾을 수 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT400_2", description = "재고가 부족합니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT400_3", description = "주문 수량은 1개 이상이어야 합니다.")
    })
    @PostMapping("/items")
    public ResponseEntity<ApiResponse<CartItemResponseDto>> addCartItem(
            @RequestParam Long memberId,
            @Valid @RequestBody CartItemRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.onSuccess("장바구니에 상품이 추가되었습니다.", cartService.addCartItem(memberId, dto)));
    }

    // 장바구니 수량 변경
    @Operation(summary = "장바구니 담은 상품 수량 변경", description = "장바구니에 있는 상품의 수량을 변경합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "장바구니 수량 변경 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART404_1", description = "장바구니를 찾을 수 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART_PRODUCT404_1", description = "장바구니 상품을 찾을 수 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT400_2", description = "재고가 부족합니다.")
    })
    @PatchMapping("/items/{itemId}")
    public ResponseEntity<ApiResponse<CartItemResponseDto>> updateCartItem(
            @RequestParam Long memberId,
            @PathVariable Long itemId,
            @Valid @RequestBody CartItemUpdateDto dto) {
        return ResponseEntity.ok(ApiResponse.onSuccess("장바구니 수량이 변경되었습니다.", cartService.updateCartItem(memberId, itemId, dto)));
    }

    // 장바구니 상품 삭제
    @Operation(summary = "장바구니 상품 삭제", description = "장바구니에서 특정 상품을 삭제합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "장바구니 상품 삭제 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART404_1", description = "장바구니를 찾을 수 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART_PRODUCT404_1", description = "장바구니 상품을 찾을 수 없습니다.")
    })
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<ApiResponse<Void>> deleteCartItem(
            @RequestParam Long memberId,
            @PathVariable Long itemId) {
        cartService.deleteCartItem(memberId, itemId);
        return ResponseEntity.ok(ApiResponse.onSuccess("장바구니 상품이 삭제되었습니다."));
    }
}