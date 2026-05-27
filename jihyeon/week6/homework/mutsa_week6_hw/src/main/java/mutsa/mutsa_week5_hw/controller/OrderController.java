package mutsa.mutsa_week5_hw.controller;

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
public class OrderController {

    private final OrderService orderService;

    // 즉시 구매
    @PostMapping("/direct")
    public OrderResponseDto createDirectOrder(
            @RequestParam Long memberId,
            @RequestBody @Valid OrderDirectRequestDto dto
    ) {

        return orderService.createDirectOrder(memberId, dto);
    }


    // 장바구니 주문
    @PostMapping("/cart")
    public OrderResponseDto createOrderFromCart(
            @RequestParam Long memberId,
            @RequestBody @Valid OrderFromCartRequestDto dto
    ) {

        return orderService.createOrderFromCart(memberId, dto);
    }

    // 결제 완료 처리
    @PatchMapping("/{orderId}/pay")
    public ResponseEntity<OrderResponseDto> payOrder(
            @RequestParam Long memberId,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(orderService.payOrder(memberId, orderId));
    }

    // 배송 완료 처리
    @PatchMapping("/{orderId}/deliver")
    public ResponseEntity<OrderResponseDto> deliverOrder(
            @RequestParam Long memberId,
            @PathVariable Long orderId
    ) {
        return ResponseEntity.ok(orderService.deliverOrder(memberId, orderId));
    }

    // 주문 목록 조회
    @GetMapping
    public List<OrderResponseDto> getOrders(
            @RequestParam Long memberId
    ) {

        return orderService.getOrders(memberId);
    }


    // 주문 단건 조회
    @GetMapping("/{orderId}")
    public OrderResponseDto getOrder(
            @RequestParam Long memberId,
            @PathVariable Long orderId
    ) {

        return orderService.getOrder(memberId, orderId);
    }


    // 주문 취소
    @PatchMapping("/{orderId}/cancel")
    public void cancelOrder(
            @RequestParam Long memberId,
            @PathVariable Long orderId
    ) {

        orderService.cancelOrder(memberId, orderId);
    }
}
