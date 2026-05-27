package com.study.shop.controller;

import com.study.shop.dto.order.*;
import com.study.shop.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/cart")
    public ResponseEntity<OrderResponse> createCartOrder(
            @RequestParam Long memberId,
            @Valid @RequestBody CartOrderRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createCartOrder(memberId, request));
    }

    @PostMapping("/direct")
    public ResponseEntity<OrderResponse> createDirectOrder(
            @RequestParam Long memberId,
            @Valid @RequestBody DirectOrderRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.createDirectOrder(memberId, request));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrders(@RequestParam Long memberId) {
        return ResponseEntity.ok(orderService.getOrders(memberId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrder(
            @RequestParam Long memberId,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(orderService.getOrder(memberId, orderId));
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<OrderStatusResponse> cancelOrder(
            @RequestParam Long memberId,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(orderService.cancelOrder(memberId, orderId));
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderStatusResponse> updateOrderStatus(
            @RequestParam Long memberId,
            @PathVariable Long orderId,
            @Valid @RequestBody UpdateOrderStatusRequest request
    ) {
        return ResponseEntity.ok(orderService.updateOrderStatus(memberId, orderId, request));
    }
}
