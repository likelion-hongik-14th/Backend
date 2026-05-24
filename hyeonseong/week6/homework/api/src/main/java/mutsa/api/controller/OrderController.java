package mutsa.api.controller;

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
public class OrderController {

    private final OrderService orderService;

    // 단일 상품 즉시 주문 API
    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createOrder(
            @RequestParam Long userId,
            @Valid @RequestBody OrderRequestDto requestDto){

        Long orderId = orderService.createOrder(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess(OrderSuccessCode.ORDER_CREATED, orderId));
    }

    // 장바구니 내역 전체 주문 API
    @PostMapping("/from-cart")
    public ResponseEntity<ApiResponse<Long>> createOrderFromCart(
            @RequestParam Long userId,
            @RequestParam Long addressId) {

        Long orderId = orderService.createOrderFromCart(userId, addressId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess(OrderSuccessCode.ORDER_CREATED, orderId));
    }

    // 내 주문 이력 목록 조회 API
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> getMyOrders(
            @RequestParam Long userId
    ) {
        List<OrderResponseDto> response = orderService.getMyOrders(userId);
        return ResponseEntity.ok(ApiResponse.onSuccess(OrderSuccessCode.ORDER_OK, response));
    }

    // 진행 중인 주문 취소 API (배송 완료 전까지만 가능)
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(
            @RequestParam Long userId,
            @PathVariable Long orderId){
        orderService.cancelOrder(orderId, userId);
        return ResponseEntity.ok(ApiResponse.onSuccess(OrderSuccessCode.ORDER_CANCELED));
    }

    // [관리자] 주문 건 배송 완료 처리 API
    @PatchMapping("/{orderId}/delivery")
    public ResponseEntity<ApiResponse<Void>> completeDelivery(
            @RequestParam Long userId,
            @PathVariable Long orderId) {
        orderService.completeDelivery(orderId, userId);
        return ResponseEntity.ok(ApiResponse.onSuccess(OrderSuccessCode.ORDER_DELIVERED));
    }
}
