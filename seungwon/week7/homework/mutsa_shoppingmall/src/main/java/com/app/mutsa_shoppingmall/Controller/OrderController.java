package com.app.mutsa_shoppingmall.Controller;

import com.app.mutsa_shoppingmall.DTO.OrderDto;
import com.app.mutsa_shoppingmall.Service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Order", description = "주문 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "장바구니 전체 주문", description = "장바구니에 담긴 모든 상품을 주문합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "주문 성공"),
            @ApiResponse(responseCode = "400", description = "CART_4001 - 장바구니가 비어있습니다."),
            @ApiResponse(responseCode = "400", description = "PRODUCT_4003 - 재고가 부족합니다."),
            @ApiResponse(responseCode = "404", description = "CART_4041 - 장바구니가 존재하지 않습니다.")
    })
    @PostMapping("/cart")
    public ResponseEntity<OrderDto.Response> orderFromCart() {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.orderFromCart());
    }

    @Operation(summary = "즉시 주문", description = "특정 상품을 바로 주문합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "주문 성공"),
            @ApiResponse(responseCode = "400", description = "PRODUCT_4003 - 재고가 부족합니다."),
            @ApiResponse(responseCode = "404", description = "PRODUCT_4041 - 상품을 찾을 수 없습니다.")
    })
    @PostMapping
    public ResponseEntity<OrderDto.Response> orderDirect(@RequestBody OrderDto.CreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.orderDirect(request));
    }

    @Operation(summary = "주문 단건 조회", description = "주문 ID로 특정 주문을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "ORDER_4041 - 주문을 찾을 수 없습니다.")
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto.Response> getOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrder(orderId));
    }

    @Operation(summary = "주문 취소", description = "주문을 취소합니다. 배송 완료 상태에서는 취소 불가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "취소 성공"),
            @ApiResponse(responseCode = "400", description = "ORDER_4002 - 배송 완료된 주문은 취소할 수 없습니다."),
            @ApiResponse(responseCode = "404", description = "ORDER_4041 - 주문을 찾을 수 없습니다.")
    })
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<OrderDto.Response> cancelOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }
}