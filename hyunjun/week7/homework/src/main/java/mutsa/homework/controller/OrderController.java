package mutsa.homework.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.homework.controller.docs.OrderApiDocs;
import mutsa.homework.dto.order.AddOrderRequestDto;
import mutsa.homework.dto.order.CartOrderRequestDto;
import mutsa.homework.dto.order.OrderResponseDto;
import mutsa.homework.global.apiPayload.GlobalResponse;
import mutsa.homework.global.dto.ListResponseDto;
import mutsa.homework.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController implements OrderApiDocs {

    private final OrderService orderService;

    @Override
    @PostMapping("/direct")
    public ResponseEntity<GlobalResponse<OrderResponseDto>> addProductToOrder(
            @RequestHeader("X-User-Id") Long userId,
            @Valid
            @RequestBody AddOrderRequestDto requestDto
    ) {

        OrderResponseDto responseDto = orderService.addProductToOrder(userId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GlobalResponse.onSuccessCreate(responseDto));
    }

    @Override
    @PostMapping("/cart")
    public ResponseEntity<GlobalResponse<OrderResponseDto>> addCartItemToOrder(
            @RequestHeader("X-User-Id") Long userId,
            @Valid
            @RequestBody CartOrderRequestDto requestDto
    ) {

        OrderResponseDto responseDto = orderService.addCartItemToOrder(userId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GlobalResponse.onSuccessCreate(responseDto));
    }

    @Override
    @GetMapping
    public ResponseEntity<GlobalResponse<ListResponseDto<OrderResponseDto>>> getOrder(
            @RequestHeader("X-User-Id") Long userId
    ) {
        ListResponseDto<OrderResponseDto> responseDto = orderService.getOrder(userId);

        return ResponseEntity.ok(GlobalResponse.onSuccess(responseDto));
    }

    @Override
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<GlobalResponse<Void>> cancelOrder(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable("orderId") Long orderId
    ) {
        orderService.cancelOrder(userId, orderId);

        return ResponseEntity.ok(GlobalResponse.onSuccess());
    }
}

