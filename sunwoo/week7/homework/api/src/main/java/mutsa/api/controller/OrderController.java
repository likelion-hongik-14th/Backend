package mutsa.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.api.dto.CartOrderRequestDto;
import mutsa.api.dto.DirectOrderRequestDto;
import mutsa.api.dto.OrderResponseDto;
import mutsa.api.global.apiPayload.ApiResponse;
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
@Tag(name = "Order", description = "Order API")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/cart")
    @Operation(summary = "장바구니 주문 생성", description = "장바구니에 담긴 상품들로 주문을 생성하는 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON2000", description = "장바구니 주문 생성에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_4001", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_4041", description = "유저를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ADDRESS_4041", description = "주소를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART_4041", description = "장바구니를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CART_4001", description = "장바구니가 비어 있습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT_4001", description = "상품 재고가 부족합니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrderFromCart(
            @PathVariable Long userId,
            @RequestBody @Valid CartOrderRequestDto requestDto
    ) {
        OrderResponseDto responseDto = orderService.createOrderFromCart(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.onSuccess("장바구니 주문 생성에 성공했습니다.", responseDto));
    }

    @PostMapping("/direct")
    @Operation(summary = "직접 주문 생성", description = "상품을 바로 주문하는 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON2000", description = "직접 주문 생성에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_4001", description = "잘못된 요청입니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_4041", description = "유저를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ADDRESS_4041", description = "주소를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT_4041", description = "상품을 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT_4001", description = "상품 재고가 부족합니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<OrderResponseDto>> createDirectOrder(
            @PathVariable Long userId,
            @RequestBody @Valid DirectOrderRequestDto requestDto
    ) {
        OrderResponseDto responseDto = orderService.createDirectOrder(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.onSuccess("직접 주문 생성에 성공했습니다.", responseDto));
    }

    @GetMapping
    @Operation(summary = "주문 목록 조회", description = "유저의 주문 목록을 조회하는 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON2000", description = "주문 목록 조회에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_4041", description = "유저를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> getOrders(@PathVariable Long userId) {
        List<OrderResponseDto> orders = orderService.getOrders(userId);
        return ResponseEntity.ok(ApiResponse.onSuccess("주문 목록 조회에 성공했습니다.", orders));
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "주문 상세 조회", description = "유저의 특정 주문 상세 정보를 조회하는 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON2000", description = "주문 상세 조회에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_4041", description = "유저를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ORDER_4041", description = "주문을 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<OrderResponseDto>> getOrder(
            @PathVariable Long userId,
            @PathVariable Long orderId
    ) {
        OrderResponseDto order = orderService.getOrder(userId, orderId);
        return ResponseEntity.ok(ApiResponse.onSuccess("주문 상세 조회에 성공했습니다.", order));
    }

    @PatchMapping("/{orderId}/cancel")
    @Operation(summary = "주문 취소", description = "유저의 주문을 취소하고 상품 재고를 복구하는 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON2000", description = "주문 취소에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_4041", description = "유저를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ORDER_4041", description = "주문을 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ORDER_4001", description = "이미 취소된 주문입니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ORDER_4002", description = "배송 완료된 주문은 변경할 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<OrderResponseDto>> cancelOrder(
            @PathVariable Long userId,
            @PathVariable Long orderId
    ) {
        OrderResponseDto order = orderService.cancelOrder(userId, orderId);
        return ResponseEntity.ok(ApiResponse.onSuccess("주문 취소에 성공했습니다.", order));
    }

    @PatchMapping("/{orderId}/payment")
    @Operation(summary = "결제 완료", description = "유저의 주문 상태를 결제 완료로 변경하는 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON2000", description = "결제 완료에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_4041", description = "유저를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ORDER_4041", description = "주문을 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<OrderResponseDto>> completePayment(
            @PathVariable Long userId,
            @PathVariable Long orderId
    ) {
        OrderResponseDto order = orderService.completePayment(userId, orderId);
        return ResponseEntity.ok(ApiResponse.onSuccess("결제 완료에 성공했습니다.", order));
    }

    @PatchMapping("/{orderId}/delivery")
    @Operation(summary = "배송 완료", description = "유저의 주문 상태를 배송 완료로 변경하는 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON2000", description = "배송 완료에 성공했습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_4041", description = "유저를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ORDER_4041", description = "주문을 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<OrderResponseDto>> completeDelivery(
            @PathVariable Long userId,
            @PathVariable Long orderId
    ) {
        OrderResponseDto order = orderService.completeDelivery(userId, orderId);
        return ResponseEntity.ok(ApiResponse.onSuccess("배송 완료에 성공했습니다.", order));
    }
}
