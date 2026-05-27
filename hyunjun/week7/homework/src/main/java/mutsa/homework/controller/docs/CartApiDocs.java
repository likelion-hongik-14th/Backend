package mutsa.homework.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import mutsa.homework.dto.cart.AddCartItemRequestDto;
import mutsa.homework.dto.cart.CartItemResponseDto;
import mutsa.homework.dto.cart.CartResponseDto;
import mutsa.homework.dto.cart.UpdateQuantityRequestDto;
import mutsa.homework.global.apiPayload.GlobalResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "Cart API", description = "장바구니 도메인 API")
public interface CartApiDocs  {

    @Operation(summary = "장바구니 상품 추가", description = "장바구니에 상품을 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "장바구니 상품 추가 성공(COMMON_201_1)"),
            @ApiResponse(
                    responseCode = "404",
                    description = "사용자 조회 실패(USER_404_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "상품 조회 실패(PRODUCT_404_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "상품 재고 부족(PRODUCT_400_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            )
    })
    ResponseEntity<GlobalResponse<CartItemResponseDto>> createCartItem(Long userId, AddCartItemRequestDto requestDto);

    @Operation(summary = "장바구니 조회", description = "사용자의 장바구니를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "장바구니 상품 조회 성공(COMMON_200_1)"),
            @ApiResponse(
                    responseCode = "404",
                    description = "사용자 조회 실패(USER_404_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            )
    })
    ResponseEntity<GlobalResponse<CartResponseDto>> getCartItems(Long userId);

    @Operation(summary = "장바구니 상품 수량 변경", description = "장바구니 내 상품의 수량을 변경합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "장바구니 상품 수량 변경 성공(COMMON_200_1)"),
            @ApiResponse(
                    responseCode = "404",
                    description = "장바구니 조회 실패(CART_404_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "사용자 권한 없음(USER_403_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "상품 재고 부족(PRODUCT_400_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            )
    })
    ResponseEntity<GlobalResponse<CartItemResponseDto>> patchCartItem(Long userId, Long itemId, UpdateQuantityRequestDto requestDto);

    @Operation(summary = "장바구니 상품 삭제", description = "장바구니에 담은 상품을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "장바구니 상품 수량 변경 성공(COMMON_200_1)"),
            @ApiResponse(
                    responseCode = "404",
                    description = "장바구니 조회 실패(CART_404_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "사용자 권한 없음(USER_403_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            )
    })
    ResponseEntity<GlobalResponse<Void>> deleteCartItem(Long userId, Long itemId);
}
