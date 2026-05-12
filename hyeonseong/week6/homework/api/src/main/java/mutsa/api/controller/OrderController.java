package mutsa.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.api.dto.ApiResponseDto;
import mutsa.api.dto.OrderRequestDto;
import mutsa.api.dto.OrderResponseDto;
import mutsa.api.service.OrderService;
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
    public ResponseEntity<ApiResponseDto> createOrder(@Valid @RequestBody OrderRequestDto requestDto){
        Long orderId = orderService.createOrder(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseDto("주문 성공!", orderId));
    }

    // 장바구니 내역 전체 주문 API
    @PostMapping("/from-cart")
    public ResponseEntity<ApiResponseDto> createOrderFromCart(@RequestParam Long addressId) {
        Long orderId = orderService.createOrderFromCart(addressId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseDto("장바구니 주문 성공!", orderId));
    }

    // 내 주문 이력 목록 조회 API
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getMyOrders() {
        List<OrderResponseDto> response = orderService.getMyOrders();
        return ResponseEntity.ok(response);
    }

    // 진행 중인 주문 취소 API (배송 완료 전까지만 가능)
    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponseDto> cancelOrder(@PathVariable Long orderId){
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok(new ApiResponseDto("주문이 정상적으로 취소되었습니다.", null));
    }

    // [관리자] 주문 건 배송 완료 처리 API
    @PostMapping("/{orderId}/delivery")
    public ResponseEntity<ApiResponseDto> completeDelivery(@PathVariable Long orderId) {
        orderService.completeDelivery(orderId);
        return ResponseEntity.ok(new ApiResponseDto("배송 완료 처리가 되었습니다.", null));
    }
}
