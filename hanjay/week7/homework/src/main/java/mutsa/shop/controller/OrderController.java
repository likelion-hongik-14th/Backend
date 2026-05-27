package mutsa.shop.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mutsa.shop.dto.CartOrderRequestDto;
import mutsa.shop.dto.DirectOrderRequestDto;
import mutsa.shop.dto.OrderResponseDto;
import mutsa.shop.global.apiPayload.ApiResponse;
import mutsa.shop.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final Long TEMP_MEMBER_ID = 1L;

    @PostMapping("/cart")
    @Operation(summary = "장바구니 상품 주문", description = "장바구니에 담긴 상품을 주문할 때 사용하는 API")
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrderFromCart(
            @RequestBody CartOrderRequestDto requestDto) {

        OrderResponseDto responseDto = orderService.createOrderFromCart(
                TEMP_MEMBER_ID,
                requestDto.getAddressId(),
                requestDto.getCartItemIds()
        );

        // ★ ApiResponse.onSuccess()로 결과 데이터를 감싸서 반환합니다.
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess("장바구니 주문에 성공했습니다.", responseDto));
    }


    @PostMapping("/direct")
    @Operation(summary = "상품 직접 주문", description = "상품을 바로 주문할 때 사용하는 API")
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrderDirect(
            @RequestBody DirectOrderRequestDto requestDto) {

        OrderResponseDto responseDto = orderService.createOrderDirect(
                TEMP_MEMBER_ID,
                requestDto.getAddressId(),
                requestDto.getProductId(),
                requestDto.getQuantity()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess("직접 주문에 성공했습니다.", responseDto));
    }

    @PatchMapping("/{orderId}/cancel")
    @Operation(summary = "주문 취소", description = "주문 취소시 사용하는 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON2000", description = "주문 취소에 성공하였습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ORDER4041", description = "해당 주문이 존재하지 않습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<Void>> cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);


        return ResponseEntity.ok()
                .body(ApiResponse.onSuccess("주문 취소에 성공했습니다."));
    }
}
