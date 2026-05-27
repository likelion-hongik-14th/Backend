package mutsa.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.api.dto.CartOrderRequestDto;
import mutsa.api.dto.DirectOrderRequestDto;
import mutsa.api.dto.OrderResponseDto;
import mutsa.api.global.apiPayload.ApiResponse;
import mutsa.api.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/cart")
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrderFromCart(
            @PathVariable Long userId,
            @RequestBody @Valid CartOrderRequestDto requestDto
    ) {
        OrderResponseDto responseDto = orderService.createOrderFromCart(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.onSuccess("장바구니 주문 생성에 성공했습니다.", responseDto));
    }

    @PostMapping("/direct")
    public ResponseEntity<ApiResponse<OrderResponseDto>> createDirectOrder(
            @PathVariable Long userId,
            @RequestBody @Valid DirectOrderRequestDto requestDto
    ) {
        OrderResponseDto responseDto = orderService.createDirectOrder(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.onSuccess("직접 주문 생성에 성공했습니다.", responseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> getOrders(@PathVariable Long userId) {
        List<OrderResponseDto> orders = orderService.getOrders(userId);
        return ResponseEntity.ok(ApiResponse.onSuccess("주문 목록 조회에 성공했습니다.", orders));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> getOrder(
            @PathVariable Long userId,
            @PathVariable Long orderId
    ) {
        OrderResponseDto order = orderService.getOrder(userId, orderId);
        return ResponseEntity.ok(ApiResponse.onSuccess("주문 상세 조회에 성공했습니다.", order));
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<OrderResponseDto>> cancelOrder(
            @PathVariable Long userId,
            @PathVariable Long orderId
    ) {
        OrderResponseDto order = orderService.cancelOrder(userId, orderId);
        return ResponseEntity.ok(ApiResponse.onSuccess("주문 취소에 성공했습니다.", order));
    }

    @PatchMapping("/{orderId}/payment")
    public ResponseEntity<ApiResponse<OrderResponseDto>> completePayment(
            @PathVariable Long userId,
            @PathVariable Long orderId
    ) {
        OrderResponseDto order = orderService.completePayment(userId, orderId);
        return ResponseEntity.ok(ApiResponse.onSuccess("결제 완료에 성공했습니다.", order));
    }

    @PatchMapping("/{orderId}/delivery")
    public ResponseEntity<ApiResponse<OrderResponseDto>> completeDelivery(
            @PathVariable Long userId,
            @PathVariable Long orderId
    ) {
        OrderResponseDto order = orderService.completeDelivery(userId, orderId);
        return ResponseEntity.ok(ApiResponse.onSuccess("배송 완료에 성공했습니다.", order));
    }
}
