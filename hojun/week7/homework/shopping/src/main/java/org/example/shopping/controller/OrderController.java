package org.example.shopping.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.shopping.dto.order.DirectOrderRequestDto;
import org.example.shopping.dto.order.OrderRequestDto;
import org.example.shopping.dto.order.OrderResponseDto;
import org.example.shopping.global.apiPayload.ApiResponse;
import org.example.shopping.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Order API", description = "주문 도메인 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "장바구니 주문", description = "장바구니에 담긴 모든 상품을 주문한다")
    @PostMapping("/{userId}")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CREATED", description = "장바구니 주문이 완료되었습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_NOT_FOUND", description = "존재하지 않는 회원입니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART_NOT_FOUND", description = "장바구니 정보가 존재하지 않습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT_OUT_OF_STOCK", description = "장바구니에 담긴 상품 중 일부의 재고가 부족합니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(@PathVariable Long userId, @RequestBody OrderRequestDto request) {
        OrderResponseDto response = orderService.createOrderFromCart(userId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess("장바구니 주문이 완료되었습니다.", response));
    }

    @Operation(summary = "즉시 주문", description = "개별 상품을 원하는 수량만큼 즉시 주문한다")
    @PostMapping("/{userId}/direct")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CREATED", description = "즉시 주문이 완료되었습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_NOT_FOUND", description = "존재하지 않는 회원입니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT_NOT_FOUND", description = "존재하지 않는 상품입니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT_OUT_OF_STOCK", description = "주문하려는 상품의 재고가 부족합니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<OrderResponseDto>> createDirectOrder(@PathVariable Long userId, @RequestBody DirectOrderRequestDto request) {
        OrderResponseDto response = orderService.directOrder(userId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess("즉시 주문이 완료되었습니다.", response));
    }

    @Operation(summary = "주문 취소", description = "주문의 상태를 '취소'로 바꾼다")
    @PatchMapping("/{userId}/{orderId}")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SUCCESS", description = "주문이 취소되었습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_NOT_FOUND", description = "존재하지 않는 회원입니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ORDER_NOT_FOUND", description = "존재하지 않는 주문 정보입니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<Void>> cancelOrder(@PathVariable Long userId, @PathVariable Long orderId){
        orderService.cancelOrder(userId, orderId);
        return ResponseEntity
                .ok(ApiResponse.onSuccess("주문이 취소되었습니다."));
    }
}