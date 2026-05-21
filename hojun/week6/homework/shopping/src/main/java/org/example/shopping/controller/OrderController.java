package org.example.shopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.shopping.dto.order.DirectOrderRequestDto;
import org.example.shopping.dto.order.OrderRequestDto;
import org.example.shopping.dto.order.OrderResponseDto;
import org.example.shopping.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/{userId}")
    public ResponseEntity<OrderResponseDto> createOrder(@PathVariable Long userId, @RequestBody OrderRequestDto request) {
        OrderResponseDto response = orderService.createOrderFromCart(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{userId}/direct")
    public ResponseEntity<OrderResponseDto> createDirectOrder(@PathVariable Long userId, @RequestBody DirectOrderRequestDto request) {
        OrderResponseDto response = orderService.directOrder(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{orderId}")
    public ResponseEntity<Void> cancleOrder(@PathVariable Long orderId){
        orderService.cancleOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
