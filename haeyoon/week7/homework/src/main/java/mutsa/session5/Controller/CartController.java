package mutsa.session5.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.session5.Dto.CartItemRequestDto;
import mutsa.session5.Dto.CartItemResponseDto;
import mutsa.session5.Dto.CartResponseDto;
import mutsa.session5.Service.CartService;
import mutsa.session5.global.apipayload.ApiResponse;
import mutsa.session5.global.apipayload.exception.code.CartSuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "장바구니(Cart) API", description = "장바구니 담기, 조회, 수량 수정, 삭제 API")
public class CartController {
    private final CartService cartService;

    // 장바구니 상품 담기
    @PostMapping("/items")
    @Operation(summary = "장바구니 상품 추가", description = "회원 ID와 상품 ID, 수량을 받아 장바구니에 아이템을 추가")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "장바구니에 상품이 담겼습니다. (CART_201)",
                    content = @Content(mediaType = "application/json")
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "선택하신 수량이 상품의 재고를 초과했습니다. (CART_400_2)",
                    content = @Content(mediaType = "application/json")
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "회원 혹은 상품 정보를 찾을 수 없습니다. (MEMBER_404_1 / PRODUCT_404_1)",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<ApiResponse<CartItemResponseDto>> addCartItem(@Valid @RequestBody CartItemRequestDto requestDto) {
        CartItemResponseDto response = cartService.addCartItem(requestDto);
        CartSuccessCode successCode = CartSuccessCode.ADD_ITEM_SUCCESS;

        return ResponseEntity
                .status(successCode.getHttpStatus())
                .body(ApiResponse.onSuccess(successCode, response));
    }

    // 장바구니 상품 조회
    @GetMapping
    @Operation(summary = "장바구니 목록 조회", description = "회원 ID를 받아 해당 회원의 장바구니 내역 및 상품 목록을 조회")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "장바구니 목록 조회가 완료되었습니다. (CART_200_1)",
                    content = @Content(mediaType = "application/json")
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "존재하지 않거나 빈 장바구니입니다. (CART_404_1)",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<ApiResponse<CartResponseDto>> getCartResponseDto(@RequestParam Long userId) {
        CartResponseDto response = cartService.getCartResponseDto(userId);
        CartSuccessCode successCode = CartSuccessCode.GET_CART_SUCCESS;

        return ResponseEntity
                .status(successCode.getHttpStatus())
                .body(ApiResponse.onSuccess(successCode, response));
    }

    // 장바구니 상품 수량 변경
    @PatchMapping("/items/{itemId}")
    @Operation(summary = "장바구니 수량 수정", description = "장바구니 아이템 ID와 변경할 수량을 받아 수량을 업데이트")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "장바구니 수량이 변경되었습니다. (CART_200_2)",
                    content = @Content(mediaType = "application/json")
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "올바르지 않은 수량이거나 재고를 초과했습니다. (CART_400_1 / CART_400_2)",
                    content = @Content(mediaType = "application/json")
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "장바구니에서 해당 상품을 찾을 수 없습니다. (CART_404_2)",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<ApiResponse<CartItemResponseDto>> updateCartItemQuantity(@RequestParam Long memberId, @PathVariable Long itemId, @RequestBody CartItemRequestDto requestDto) {
        CartItemResponseDto response = cartService.updateCartItemQuantity(memberId, itemId, requestDto.getQuantity());
        CartSuccessCode successCode = CartSuccessCode.UPDATE_QUANTITY_SUCCESS;

        return ResponseEntity
                .status(successCode.getHttpStatus())
                .body(ApiResponse.onSuccess(successCode, response));
    }

    // 장바구니 상품 삭제
    @DeleteMapping("/items/{itemId}")
    @Operation(summary = "장바구니 상품 삭제", description = "장바구니 아이템 ID를 이용하여 해당 상품을 장바구니에서 삭제")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "장바구니 상품이 삭제되었습니다. (CART_200_3)",
                    content = @Content(mediaType = "application/json")
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "장바구니에서 해당 상품을 찾을 수 없습니다. (CART_404_2)",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<ApiResponse<Void>> deleteCartItem(@RequestParam Long memberId, @PathVariable Long itemId) {
        cartService.deleteCartItem(memberId, itemId);
        CartSuccessCode successCode = CartSuccessCode.DELETE_ITEM_SUCCESS;

        return ResponseEntity
                .status(successCode.getHttpStatus())
                .body(ApiResponse.onSuccess(successCode));
    }
}