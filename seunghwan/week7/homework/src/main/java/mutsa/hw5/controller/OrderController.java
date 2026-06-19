package mutsa.hw5.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.hw5.dto.order.OrderCartRequestDto;
import mutsa.hw5.dto.order.OrderDirectRequestDto;
import mutsa.hw5.dto.order.OrderResponseDto;
import mutsa.hw5.dto.order.OrderStatusUpdateDto;
import mutsa.hw5.global.apiPayload.ApiResponse;
import mutsa.hw5.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
@Tag(name = "Order", description = "주문 관련 API")
public class OrderController {

    private final OrderService orderService;

    // 장바구니 주문
    @Operation(summary = "장바구니 주문", description = "장바구니에 담긴 상품으로 주문합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "장바구니 주문 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "MEMBER404_1: 회원을 찾을 수 없습니다. / ADDRESS404_1: 배송지를 찾을 수 없습니다. / CART404_1: 장바구니를 찾을 수 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "CART400_1: 장바구니가 비어있습니다. / PRODUCT400_2: 재고가 부족합니다.")
    })
    @PostMapping("/cart")
    public ResponseEntity<ApiResponse<OrderResponseDto>> orderFromCart(@Valid @RequestBody OrderCartRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.onSuccess("장바구니 주문이 완료되었습니다.", orderService.orderFromCart(dto)));
    }

    // 즉시 주문
    @Operation(summary = "즉시 주문", description = "상품을 바로 주문합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "즉시 주문 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "MEMBER404_1: 회원을 찾을 수 없습니다. / ADDRESS404_1: 배송지를 찾을 수 없습니다. / PRODUCT404_1: 상품을 찾을 수 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "PRODUCT400_2: 재고가 부족합니다. / PRODUCT400_3: 주문 수량은 1개 이상이어야 합니다.")
    })
    @PostMapping("/direct")
    public ResponseEntity<ApiResponse<OrderResponseDto>> orderDirect(@Valid @RequestBody OrderDirectRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.onSuccess("즉시 주문이 완료되었습니다.", orderService.orderDirect(dto)));
    }

    // 주문 상세 조회
    @Operation(summary = "주문 상세 조회", description = "특정 주문의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "주문 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "ORDER404_1: 주문을 찾을 수 없습니다.")
    })
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> getOrder(
            @PathVariable Long orderId,
            @RequestParam Long memberId) {
        return ResponseEntity.ok(ApiResponse.onSuccess("주문 조회 성공", orderService.getOrder(orderId, memberId)));
    }

    // 주문 상태 변경
    @Operation(summary = "주문 상태 변경", description = "주문의 상태를 변경합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "주문 상태 변경 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "ORDER404_1: 주문을 찾을 수 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "ORDER400_1: 해당 상태로 변경할 수 없습니다.")
    })
    @PatchMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> changeOrderStatus(
            @PathVariable Long orderId,
            @RequestParam Long memberId,
            @Valid @RequestBody OrderStatusUpdateDto dto) {
        return ResponseEntity.ok(ApiResponse.onSuccess("주문 상태가 변경되었습니다.", orderService.changeOrderStatus(orderId, memberId, dto)));
    }
}
