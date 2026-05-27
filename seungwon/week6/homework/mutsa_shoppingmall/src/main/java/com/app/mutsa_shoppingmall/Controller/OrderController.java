package com.app.mutsa_shoppingmall.Controller;

import com.app.mutsa_shoppingmall.DTO.OrderDto;
import com.app.mutsa_shoppingmall.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    // 장바구니 전체 주문
    @PostMapping("/cart")
    public ResponseEntity<OrderDto.Response> orderFromCart() {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.orderFromCart());
    }

    // 즉시 주문
    @PostMapping
    public ResponseEntity<OrderDto.Response> orderDirect(@RequestBody OrderDto.CreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.orderDirect(request));
    }

    // 주문 단건 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto.Response> getOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    // 주문 취소
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<OrderDto.Response> cancelOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }
}