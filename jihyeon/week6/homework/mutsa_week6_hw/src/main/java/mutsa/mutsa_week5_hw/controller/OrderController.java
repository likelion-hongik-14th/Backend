package mutsa.mutsa_week5_hw.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.mutsa_week5_hw.dto.OrderDirectRequestDto;
import mutsa.mutsa_week5_hw.dto.OrderFromCartRequestDto;
import mutsa.mutsa_week5_hw.dto.OrderResponseDto;
import mutsa.mutsa_week5_hw.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "주문 API")
public class OrderController {

    private final OrderService orderService;

    // 즉시 구매
    @Operation(summary = "단일 상품 즉시 구매", description = "단일 상품을 즉시 구매할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "즉시 주문 성공"),
            @ApiResponse(responseCode = "404", description = "PRODUCT_NOT_FOUND / ADDRESS_NOT_FOUND"),
            @ApiResponse(responseCode = "400", description = "PRODUCT_OUT_OF_STOCK")
    })
    @PostMapping("/direct")
    public OrderResponseDto createDirectOrder(
            @RequestParam Long memberId,
            @RequestBody @Valid OrderDirectRequestDto dto
    ) {

        return orderService.createDirectOrder(memberId, dto);
    }


    // 장바구니 주문
    @Operation(summary = "장바구니 상품 주문", description = "장바구니에 담은 상품을 전부 주문할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "즉시 주문 성공"),
            @ApiResponse(responseCode = "404", description = "PRODUCT_NOT_FOUND / ADDRESS_NOT_FOUND"),
            @ApiResponse(responseCode = "400", description = "PRODUCT_OUT_OF_STOCK")
    })
    @PostMapping("/cart")
    public OrderResponseDto createOrderFromCart(
            @RequestParam Long memberId,
            @RequestBody @Valid OrderFromCartRequestDto dto
    ) {

        return orderService.createOrderFromCart(memberId, dto);
    }

    // 결제 완료 처리
    @Operation(summary = "주문 결제 완료 처리", description = "주문 건을 결제 완료 처리할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "결제 완료"),
            @ApiResponse(responseCode = "404", description = "ORDER_NOT_FOUND"),
            @ApiResponse(responseCode = "400", description = "ORDER_ALREADY_CANCELED")
    })
    @PatchMapping("/{orderId}/pay")
    public ResponseEntity<OrderResponseDto> payOrder(
            @RequestParam Long memberId,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(orderService.payOrder(memberId, orderId));
    }

    // 배송 완료 처리
    @Operation(summary = "주문 배송 완료 처리", description = "주문 건을 배송 완료 처리할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "배송 완료 처리"),
            @ApiResponse(responseCode = "404", description = "ORDER_NOT_FOUND"),
            @ApiResponse(responseCode = "400", description = "ORDER_CANCEL_NOT_ALLOWED / ORDER_ALREADY_DELIVERED")
    })
    @PatchMapping("/{orderId}/deliver")
    public ResponseEntity<OrderResponseDto> deliverOrder(
            @RequestParam Long memberId,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(orderService.deliverOrder(memberId, orderId));
    }

    // 주문 목록 조회
    @Operation(summary = "주문 목록 조회", description = "주문한 내역을 조회할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "ORDER_NOT_FOUND")
    })
    @GetMapping
    public List<OrderResponseDto> getOrders(
            @RequestParam Long memberId
    ) {

        return orderService.getOrders(memberId);
    }


    // 주문 단건 조회
    @Operation(summary = "주문 단건 조회", description = "주문 건을 단일 조회할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "404", description = "ORDER_NOT_FOUND")
    })
    @GetMapping("/{orderId}")
    public OrderResponseDto getOrder(
            @RequestParam Long memberId,
            @PathVariable Long orderId
    ) {

        return orderService.getOrder(memberId, orderId);
    }


    // 주문 취소
    @Operation(summary = "주문 취소", description = "주문 건을 취소할 때 사용하는 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "취소 성공"),
            @ApiResponse(responseCode = "404", description = "ORDER_NOT_FOUND"),
            @ApiResponse(responseCode = "400", description = "ORDER_CANCEL_NOT_ALLOWED / ORDER_ALREADY_CANCELED")
    })
    @PatchMapping("/{orderId}/cancel")
    public void cancelOrder(
            @RequestParam Long memberId,
            @PathVariable Long orderId
    ) {

        orderService.cancelOrder(memberId, orderId);
    }
}
