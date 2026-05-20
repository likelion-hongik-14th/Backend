package mutsa.homework.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.homework.dto.order.AddOrderRequestDto;
import mutsa.homework.dto.order.CartOrderRequestDto;
import mutsa.homework.dto.order.OrderResponseDto;
import mutsa.homework.global.dto.ApiResponse;
import mutsa.homework.global.dto.ListResponseDto;
import mutsa.homework.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/direct")
    public ResponseEntity<ApiResponse<OrderResponseDto>> addProductToOrder(
            @RequestHeader("X-User-Id") Long userId,
            @Valid
            @RequestBody AddOrderRequestDto requestDto
    ) {

        OrderResponseDto responseDto = orderService.addProductToOrder(userId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(responseDto));
    }

    @PostMapping("/cart")
    public ResponseEntity<ApiResponse<OrderResponseDto>> addCartItemToOrder(
            @RequestHeader("X-User-Id") Long userId,
            @Valid
            @RequestBody CartOrderRequestDto requestDto
    ) {

        OrderResponseDto responseDto = orderService.addCartItemToOrder(userId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(responseDto));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ListResponseDto<OrderResponseDto>>> getOrder(
            @RequestHeader("X-User-Id") Long userId
    ) {
        ListResponseDto<OrderResponseDto> responseDto = orderService.getOrder(userId);

        return ResponseEntity.ok(ApiResponse.success(responseDto));
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable("orderId") Long orderId
    ) {
        orderService.cancelOrder(userId, orderId);

        return ResponseEntity.ok(ApiResponse.success(null));
    }
}

