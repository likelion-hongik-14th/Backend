package mutsa.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.api.dto.OrderRequestDto;
import mutsa.api.dto.OrderResponseDto;
import mutsa.api.global.apiPayload.code.OrderSuccessCode;
import mutsa.api.service.OrderService;
import mutsa.api.global.apiPayload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Order API", description = "주문 및 결제 관련 API")
public class OrderController {

    private final OrderService orderService;

    // 단일 상품 즉시 주문 API
    @PostMapping
    @Operation(summary = "단일 상품 바로 주문", description = "특정 상품을 장바구니를 거치지 않고 즉시 주문합니다.")
    public ResponseEntity<ApiResponse<Long>> createOrder(
            @RequestParam Long userId,
            @Valid @RequestBody OrderRequestDto requestDto){

        Long orderId = orderService.createOrder(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess(OrderSuccessCode.ORDER_CREATED, orderId));
    }

    // 장바구니 내역 전체 주문 API
    @PostMapping("/from-cart")
    @Operation(summary = "장바구니 전체 주문", description = "장바구니에 담긴 모든 품목을 한꺼번에 주문합니다.")
    public ResponseEntity<ApiResponse<Long>> createOrderFromCart(
            @RequestParam Long userId,
            @RequestParam Long addressId) {

        Long orderId = orderService.createOrderFromCart(userId, addressId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess(OrderSuccessCode.ORDER_CREATED, orderId));
    }

    // 내 주문 이력 목록 조회 API
    @GetMapping
    @Operation(summary = "내 주문 내역 조회", description = "로그인한 유저의 과거 주문 이력을 조회합니다.")
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> getMyOrders(
            @RequestParam Long userId
    ) {
        List<OrderResponseDto> response = orderService.getMyOrders(userId);
        return ResponseEntity.ok(ApiResponse.onSuccess(OrderSuccessCode.ORDER_OK, response));
    }

    // 진행 중인 주문 취소 API (배송 완료 전까지만 가능)
    @PatchMapping("/{orderId}/cancel")
    @Operation(summary = "주문 취소", description = "배송 시작 전 주문을 취소하고 재고를 복구합니다.")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(
            @RequestParam Long userId,
            @PathVariable Long orderId){
        orderService.cancelOrder(orderId, userId);
        return ResponseEntity.ok(ApiResponse.onSuccess(OrderSuccessCode.ORDER_CANCELED));
    }

    // [관리자] 주문 건 배송 완료 처리 API
    @PatchMapping("/{orderId}/delivery")
    @Operation(summary = "배송 완료 처리", description = "배송이 완료된 주문의 상태를 업데이트합니다.")
    public ResponseEntity<ApiResponse<Void>> completeDelivery(
            @RequestParam Long userId,
            @PathVariable Long orderId) {
        orderService.completeDelivery(orderId, userId);
        return ResponseEntity.ok(ApiResponse.onSuccess(OrderSuccessCode.ORDER_DELIVERED));
    }

    @PatchMapping("/{orderId}/pay")
    @Operation(summary = "주문 결제", description = "주문 대기 상태인 주문을 결제 완료 상태로 변경합니다.")
    public ResponseEntity<ApiResponse<Void>> payOrder(
            @RequestParam Long userId,
            @PathVariable Long orderId) {
        orderService.payOrder(orderId, userId);
        return ResponseEntity.ok(ApiResponse.onSuccess(OrderSuccessCode.ORDER_PAID));
    }
}
