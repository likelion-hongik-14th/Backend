package mutsa.w5homework.controller;

import lombok.RequiredArgsConstructor;
import mutsa.w5homework.dto.OrderDto;
import mutsa.w5homework.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/cart")
    public ResponseEntity<OrderDto.Response> createOrderFromCart(@RequestBody OrderDto.CartOrderRequest dto) {
            return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrderFromCart(dto));
    }

    @PostMapping("/direct")
    public ResponseEntity<OrderDto.Response> createDirectOrder(@RequestBody OrderDto.DirectOrderRequest dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createDirectOrder(dto));
    }

    @PutMapping("/{id}/cancle")
    public ResponseEntity<Void> cancleOrder(@PathVariable Long id) {
        orderService.cancleOrder(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto.Response> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }
}
