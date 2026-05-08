package musta_week5.Controller;

import lombok.RequiredArgsConstructor;
import musta_week5.Dto.OrderRequestDto;
import musta_week5.Service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDto dto) {
        orderService.createOrder(dto);
        return ResponseEntity.ok("주문 완료");
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @GetMapping("/users/{id}/orders")
    public ResponseEntity<?> getOrders(@PathVariable String id) {
        return ResponseEntity.ok(orderService.getOrders(id));
    }

}
