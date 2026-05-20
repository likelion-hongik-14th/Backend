package mutsa.session5.Controller;

import lombok.RequiredArgsConstructor;
import mutsa.session5.Dto.OrderItemRequestDto;
import mutsa.session5.Dto.OrderItemResponseDto;
import mutsa.session5.Dto.OrderResponseDto;
import mutsa.session5.Service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // 주문 생성 및 저장
    @PostMapping
    public ResponseEntity<OrderItemResponseDto> createOrder(@RequestBody OrderItemRequestDto requestDto) {
        return ResponseEntity.ok(orderService.createOrder(requestDto));
    }

    // 결제 완료
    @PatchMapping("/{orderId}/confirm-payment")
    public ResponseEntity<String> confirmPayment(@PathVariable Long orderId) {
        orderService.confirmPayment(orderId);
        return ResponseEntity.ok("결제가 확인되어 주문이 확정되었습니다.");
    }

    // 배송 완료
    @PatchMapping("/{orderId}/complete-delivery")
    public ResponseEntity<String> completeDelivery(@PathVariable Long orderId) {
        orderService.completeDelivery(orderId);
        return ResponseEntity.ok("배송 완료 처리가 되었습니다.");
    }
    // 주문 취소
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok().build();
    }

    // 주문 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderResponseDto(orderId));
    }
}
