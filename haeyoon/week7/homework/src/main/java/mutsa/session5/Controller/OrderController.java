package mutsa.session5.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.session5.Dto.OrderItemRequestDto;
import mutsa.session5.Dto.OrderItemResponseDto;
import mutsa.session5.Dto.OrderResponseDto;
import mutsa.session5.Service.OrderService;
import mutsa.session5.global.apipayload.ApiResponse;
import mutsa.session5.global.apipayload.exception.code.OrderSuccessCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "주문(Order) API", description = "주문 생성, 배송, 결제 상태 변경 API")
public class OrderController {
    private final OrderService orderService;

    // 주문 생성 및 저장
    @PostMapping
    @Operation(summary = "주문 생성 및 결제 요청", description = "상품 정보와 배송지 정보를 받아 새로운 주문을 생성")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "주문이 완료되었습니다. (ORDER_201)",
                    content = @Content(mediaType = "application/json")
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "회원, 상품 혹은 주소 정보를 찾을 수 없습니다. (MEMBER_404_1 / PRODUCT_404_1 / ORDER_404_2)",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<ApiResponse<OrderItemResponseDto>> createOrder(@Valid @RequestBody OrderItemRequestDto requestDto) {
        OrderItemResponseDto response = orderService.createOrder(requestDto);
        OrderSuccessCode successCode = OrderSuccessCode.CREATE_ORDER_SUCCESS;

        return ResponseEntity
                .status(successCode.getHttpStatus())
                .body(ApiResponse.onSuccess(successCode, response));
    }

    // 결제 완료
    @PatchMapping("/{orderId}/confirm-payment")
    @Operation(summary = "결제 승인 완료", description = "주문 상태가 접수일 때 결제 승인 완료 상태로 변경")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "결제 승인이 완료되었습니다. (ORDER_200_3)",
                    content = @Content(mediaType = "application/json")
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "현재 주문 상태에서는 결제를 확인하거나 변경할 수 없습니다. (ORDER_400_1)",
                    content = @Content(mediaType = "application/json")
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "해당 주문 내역을 찾을 수 없습니다. (ORDER_404_1)",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<ApiResponse<Void>> confirmPayment(@RequestParam Long memberId, @PathVariable Long orderId) {
        orderService.confirmPayment(memberId, orderId);
        OrderSuccessCode successCode = OrderSuccessCode.CONFIRM_PAYMENT_SUCCESS;

        return ResponseEntity
                .status(successCode.getHttpStatus())
                .body(ApiResponse.onSuccess(successCode));
    }

    // 배송 완료
    @PatchMapping("/{orderId}/complete-delivery")
    @Operation(summary = "배송 완료 처리", description = "주문의 상태를 배송 완료로 변경")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "배송 완료 처리되었습니다. (ORDER_200_2)",
                    content = @Content(mediaType = "application/json")
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "해당 주문 내역을 찾을 수 없습니다. (ORDER_404_1)",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<ApiResponse<Void>> completeDelivery(@RequestParam Long memberId, @PathVariable Long orderId) {
        orderService.completeDelivery(memberId, orderId);
        OrderSuccessCode successCode = OrderSuccessCode.COMPLETE_DELIVERY_SUCCESS;

        return ResponseEntity
                .status(successCode.getHttpStatus())
                .body(ApiResponse.onSuccess(successCode));
    }
    // 주문 취소
    @PatchMapping("/{orderId}/cancel")
    @Operation(summary = "주문 취소 및 재고 복구", description = "주문을 취소하고 담겼던 상품의 재고를 원상 복구")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "주문이 취소되고 재고가 복구되었습니다. (ORDER_200_4)",
                    content = @Content(mediaType = "application/json")
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "해당 주문 내역을 찾을 수 없습니다. (ORDER_404_1)",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<ApiResponse<Void>> cancelOrder(@RequestParam Long memberId, @PathVariable Long orderId) {
        orderService.cancelOrder(memberId, orderId);
        OrderSuccessCode successCode = OrderSuccessCode.CANCEL_ORDER_SUCCESS;

        return ResponseEntity
                .status(successCode.getHttpStatus())
                .body(ApiResponse.onSuccess(successCode));
    }

    // 주문 조회
    @GetMapping("/{orderId}")
    @Operation(summary = "주문 상세 조회", description = "주문 ID를 통해 주문 내역 및 배송 상태를 상세 조회")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "주문 상세 내역 조회가 완료되었습니다. (ORDER_200_1)",
                    content = @Content(mediaType = "application/json")
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "해당 주문 내역을 찾을 수 없습니다. (ORDER_404_1)",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ResponseEntity<ApiResponse<OrderResponseDto>> getOrder(@RequestParam Long memberId, @PathVariable Long orderId) {
        OrderResponseDto response = orderService.getOrderResponseDto(memberId, orderId);
        OrderSuccessCode successCode = OrderSuccessCode.GET_ORDER_SUCCESS;

        return ResponseEntity
                .status(successCode.getHttpStatus())
                .body(ApiResponse.onSuccess(successCode));
    }
}
