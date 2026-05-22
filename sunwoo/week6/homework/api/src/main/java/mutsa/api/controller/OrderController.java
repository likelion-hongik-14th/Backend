package mutsa.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.api.dto.CartOrderRequestDto;
import mutsa.api.dto.DirectOrderRequestDto;
import mutsa.api.dto.OrderResponseDto;
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
    public ResponseEntity<OrderResponseDto> createOrderFromCart(
            @PathVariable Long userId,
            @RequestBody @Valid CartOrderRequestDto requestDto
    ) {
        OrderResponseDto responseDto = orderService.createOrderFromCart(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("/direct")
    public ResponseEntity<OrderResponseDto> createDirectOrder(
            @PathVariable Long userId,
            @RequestBody @Valid DirectOrderRequestDto requestDto
    ) {
        OrderResponseDto responseDto = orderService.createDirectOrder(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrders(userId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrder(
            @PathVariable Long userId,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(orderService.getOrder(userId, orderId));
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponseDto> cancelOrder(
            @PathVariable Long userId,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(orderService.cancelOrder(userId, orderId));
    }

    @PatchMapping("/{orderId}/payment")
    public ResponseEntity<OrderResponseDto> completePayment(
            @PathVariable Long userId,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(orderService.completePayment(userId, orderId));
    }

    @PatchMapping("/{orderId}/delivery")
    public ResponseEntity<OrderResponseDto> completeDelivery(
            @PathVariable Long userId,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(orderService.completeDelivery(userId, orderId));
    }
}
