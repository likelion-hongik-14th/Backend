package mutsa.homework.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import mutsa.homework.dto.order.AddOrderRequestDto;
import mutsa.homework.dto.order.CartOrderRequestDto;
import mutsa.homework.dto.order.OrderResponseDto;
import mutsa.homework.global.apiPayload.GlobalResponse;
import mutsa.homework.global.apiPayload.code.CartErrorCode;
import mutsa.homework.global.dto.ListResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "Order API", description = "주문 도메인 API")
public interface OrderApiDocs {

    @Operation(summary = "상품 직접 구매", description = "상품을 골라 주문합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "상품 주문 성공(COMMON_201_1)"),
            @ApiResponse(
                    responseCode = "404",
                    description = "사용자 조회 실패(USER_404_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "주소 조회 실패(ADDRESS_404_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "사용자 권한 없음(USER_403_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "상품 조회 실패(PRODUCT_404_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            )
    })
    ResponseEntity<GlobalResponse<OrderResponseDto>> addProductToOrder(
            Long userId,
            AddOrderRequestDto requestDto
    );

    @Operation(summary = "장바구니 상품 주문", description = "장바구니 내 상품을 주문합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "장바구니 상품 주문 성공(COMMON_201_1)"),
            @ApiResponse(
                    responseCode = "404",
                    description = "사용자 조회 실패(USER_404_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "주소 조회 실패(ADDRESS_404_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "사용자 권한 없음(USER_403_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "장바구니 조회 실패(CART_404_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "유효하지 않은 장바구니 상품(CART_400_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            )
    })
    ResponseEntity<GlobalResponse<OrderResponseDto>> addCartItemToOrder(
            Long userId,
            CartOrderRequestDto requestDto
    );

    @Operation(summary = "주문 목록 조회", description = "주문 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "주문 목록 조회 성공(COMMON_200_1)"),
            @ApiResponse(
                    responseCode = "404",
                    description = "사용자 조회 실패(USER_404_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            )
    })
    ResponseEntity<GlobalResponse<ListResponseDto<OrderResponseDto>>> getOrder(
            Long userId
    );

    @Operation(summary = "주문 취소", description = "주문을 취소합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "주문 취소 성공(COMMON_200_1)"),
            @ApiResponse(
                    responseCode = "404",
                    description = "주문 조회 실패(ORDER_404_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "사용자 권한 없음(USER_403_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "취소 불가(ORDER_400_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "중복 취소(ORDER_409_1)",
                    content = @Content(schema = @Schema(implementation = GlobalResponse.class))
            )
    })
    ResponseEntity<GlobalResponse<Void>> cancelOrder(
            Long userId,
            Long orderId
    );
}
