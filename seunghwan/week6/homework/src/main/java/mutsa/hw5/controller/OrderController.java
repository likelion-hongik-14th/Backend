package mutsa.hw5.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.hw5.dto.order.OrderCartRequestDto;
import mutsa.hw5.dto.order.OrderDirectRequestDto;
import mutsa.hw5.dto.order.OrderResponseDto;
import mutsa.hw5.dto.order.OrderStatusUpdateDto;
import mutsa.hw5.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    // 장바구니 주문
    @PostMapping("/cart")
    public ResponseEntity<OrderResponseDto> orderFromCart(@Valid @RequestBody OrderCartRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.orderFromCart(dto));
    }

    // 즉시 주문
    @PostMapping("/direct")
    public ResponseEntity<OrderResponseDto> orderDirect(@Valid @RequestBody OrderDirectRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.orderDirect(dto));
    }

    // 주문 상세 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrder(
            @PathVariable Long orderId,
            @RequestParam Long memberId) {
        return ResponseEntity.ok(orderService.getOrder(orderId, memberId));
    }

    // 주문 상태 변경
    @PatchMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> changeOrderStatus(
            @PathVariable Long orderId,
            @RequestParam Long memberId,
            @Valid @RequestBody OrderStatusUpdateDto dto) {
        return ResponseEntity.ok(orderService.changeOrderStatus(orderId, memberId, dto));
    }
}
