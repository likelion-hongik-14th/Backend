package mutsa.shop.controller;

import lombok.RequiredArgsConstructor;
import mutsa.shop.dto.CartOrderRequestDto;
import mutsa.shop.dto.DirectOrderRequestDto;
import mutsa.shop.dto.OrderResponseDto;
import mutsa.shop.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final Long TEMP_MEMBER_ID = 1L;

    @PostMapping("/cart")
    public ResponseEntity<OrderResponseDto> createOrderFromCart(
            @RequestBody CartOrderRequestDto requestDto) {

        // memberId 자리에 TEMP_MEMBER_ID(1L)를 주입합니다.
        OrderResponseDto responseDto = orderService.createOrderFromCart(
                TEMP_MEMBER_ID,
                requestDto.getAddressId(),
                requestDto.getCartItemIds()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 상품 직접 주문 (바로 구매)
    @PostMapping("/direct")
    public ResponseEntity<OrderResponseDto> createOrderDirect(
            @RequestBody DirectOrderRequestDto requestDto) {

        // memberId 자리에 TEMP_MEMBER_ID(1L)를 주입합니다.
        OrderResponseDto responseDto = orderService.createOrderDirect(
                TEMP_MEMBER_ID,
                requestDto.getAddressId(),
                requestDto.getProductId(),
                requestDto.getQuantity()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }


    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<Void> cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok().build(); // 성공 시 바디 없이 200 OK 반환
    }
}
