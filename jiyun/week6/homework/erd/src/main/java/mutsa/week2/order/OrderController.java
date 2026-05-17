package mutsa.week2.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.week2.common.auth.CurrentMemberProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CurrentMemberProvider currentMember;

    @PostMapping("/from-cart")
    public ResponseEntity<OrderResponseDto> createFromCart(
            @Valid @RequestBody OrderCreateFromCartRequestDto requestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createFromCart(currentMember.currentMemberId(), requestDto));
    }

    @PostMapping("/from-product")
    public ResponseEntity<OrderResponseDto> createFromProduct(
            @Valid @RequestBody OrderCreateFromProductRequestDto requestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createFromProduct(currentMember.currentMemberId(), requestDto));
    }

    @GetMapping
    public ResponseEntity<OrderListResponseDto> findByMember() {
        return ResponseEntity.ok(orderService.findByMember(currentMember.currentMemberId()));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> findById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.findById(currentMember.currentMemberId(), orderId));
    }

    @PatchMapping("/{orderId}/pay")
    public ResponseEntity<OrderResponseDto> pay(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.pay(currentMember.currentMemberId(), orderId));
    }

    @PatchMapping("/{orderId}/deliver")
    public ResponseEntity<OrderResponseDto> deliver(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.deliver(currentMember.currentMemberId(), orderId));
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponseDto> cancel(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.cancel(currentMember.currentMemberId(), orderId));
    }
}
