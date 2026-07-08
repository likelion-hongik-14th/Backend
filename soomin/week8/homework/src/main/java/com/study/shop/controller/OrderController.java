package com.study.shop.controller;

import com.study.shop.dto.order.*;
import com.study.shop.global.apiPayload.ApiResponse;
import com.study.shop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "주문 API")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "장바구니 주문", description = "장바구니 상품 목록을 기반으로 주문을 생성합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "장바구니 주문 성공",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "ORDER400_3 또는 PRODUCT400_2",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "MEMBER404_1, ADDRESS404_1, CART404_2",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/cart")
    public ResponseEntity<ApiResponse<OrderResponse>> createCartOrder(
            @RequestParam Long memberId,
            @Valid @RequestBody CartOrderRequest request
    ) {
        OrderResponse response = orderService.createCartOrder(memberId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess("장바구니 주문 성공", response));
    }

    @Operation(summary = "즉시 주문", description = "특정 상품을 바로 주문합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "즉시 주문 성공", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "PRODUCT400_2 - 상품 재고가 부족합니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "MEMBER404_1, ADDRESS404_1, PRODUCT404_1", content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/direct")
    public ResponseEntity<ApiResponse<OrderResponse>> createDirectOrder(
            @RequestParam Long memberId,
            @Valid @RequestBody DirectOrderRequest request
    ) {
        OrderResponse response = orderService.createDirectOrder(memberId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess("즉시 주문 성공", response));
    }

    @Operation(summary = "주문 목록 조회", description = "memberId를 기준으로 주문 목록을 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "주문 목록 조회 성공", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "MEMBER404_1 - 회원을 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrders(@RequestParam Long memberId) {
        List<OrderResponse> response = orderService.getOrders(memberId);

        return ResponseEntity
                .ok(ApiResponse.onSuccess("주문 목록 조회 성공", response));
    }

    @Operation(summary = "주문 상세 조회", description = "orderId와 memberId를 기준으로 주문 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "주문 상세 조회 성공", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "ORDER404_1 - 주문을 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrder(
            @RequestParam Long memberId,
            @PathVariable Long orderId
    ) {
        OrderResponse response = orderService.getOrder(memberId, orderId);

        return ResponseEntity
                .ok(ApiResponse.onSuccess("주문 상세 조회 성공", response));
    }

    @Operation(summary = "주문 취소", description = "배송 완료 전 주문을 취소하고 재고를 복구합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "주문 취소 성공", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "ORDER400_1 또는 ORDER400_2", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "ORDER404_1 - 주문을 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<OrderStatusResponse>> cancelOrder(
            @RequestParam Long memberId,
            @PathVariable Long orderId
    ) {
        OrderStatusResponse response = orderService.cancelOrder(memberId, orderId);

        return ResponseEntity
                .ok(ApiResponse.onSuccess("주문 취소 성공", response));
    }

    @Operation(summary = "주문 상태 변경", description = "주문 상태를 주문 완료, 결제 완료, 배송 완료 등으로 변경합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "주문 상태 변경 성공", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "ORDER400_4, ORDER400_5, ORDER400_6 - 변경할 수 없는 주문 상태입니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "ORDER403_1 - 다른 회원의 주문에는 접근할 수 없습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "ORDER404_1 - 주문을 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderStatusResponse>> updateOrderStatus(
            @RequestParam Long memberId,
            @PathVariable Long orderId,
            @Valid @RequestBody UpdateOrderStatusRequest request
    ) {
        OrderStatusResponse response = orderService.updateOrderStatus(memberId, orderId, request);

        return ResponseEntity
                .ok(ApiResponse.onSuccess("주문 상태 변경 성공", response));
    }
}
