package mutsa.week2.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.week2.common.auth.CurrentMemberProvider;
import mutsa.week2.global.apiPayload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order", description = "주문 API")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CurrentMemberProvider currentMember;

    @PostMapping("/from-cart")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "장바구니로부터 주문 생성",
            description = "현재 로그인 회원의 장바구니 항목들로 주문을 생성합니다. 장바구니가 비어있으면 실패합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_2001", description = "주문 생성 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ORDER_4001", description = "장바구니가 비어있어 주문할 수 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ADDRESS_4031", description = "본인의 배송지만 접근할 수 있습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ADDRESS_4041", description = "해당 배송지는 존재하지 않습니다.")
    })
    public ApiResponse<OrderResponseDto> createFromCart(
            @Valid @RequestBody OrderCreateFromCartRequestDto requestDto) {
        return ApiResponse.onSuccess("주문 생성 성공",
                orderService.createFromCart(currentMember.currentMemberId(), requestDto));
    }

    @PostMapping("/from-product")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "상품으로부터 주문 생성",
            description = "상품 ID와 수량을 받아 단일 상품 주문을 생성합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_2001", description = "주문 생성 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ORDER_4002", description = "주문 수량은 1 이상이어야 합니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ADDRESS_4031", description = "본인의 배송지만 접근할 수 있습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ADDRESS_4041", description = "해당 배송지는 존재하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT_4041", description = "해당 상품은 존재하지 않습니다.")
    })
    public ApiResponse<OrderResponseDto> createFromProduct(
            @Valid @RequestBody OrderCreateFromProductRequestDto requestDto) {
        return ApiResponse.onSuccess("주문 생성 성공",
                orderService.createFromProduct(currentMember.currentMemberId(), requestDto));
    }

    @GetMapping
    @Operation(summary = "내 주문 목록 조회", description = "현재 로그인 회원의 주문 목록을 최신순으로 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_2000", description = "주문 목록 조회 성공")
    })
    public ApiResponse<OrderListResponseDto> findByMember() {
        return ApiResponse.onSuccess("주문 목록 조회 성공",
                orderService.findByMember(currentMember.currentMemberId()));
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "주문 상세 조회", description = "orderId로 주문 단건을 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_2000", description = "주문 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ORDER_4031", description = "본인의 주문만 접근할 수 있습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ORDER_4041", description = "해당 주문은 존재하지 않습니다.")
    })
    public ApiResponse<OrderResponseDto> findById(@PathVariable Long orderId) {
        return ApiResponse.onSuccess("주문 조회 성공",
                orderService.findById(currentMember.currentMemberId(), orderId));
    }

    @PatchMapping("/{orderId}/pay")
    @Operation(summary = "주문 결제", description = "주문을 결제 완료 상태로 전환합니다. 재고가 부족하면 실패합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_2000", description = "주문 결제 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ORDER_4031", description = "본인의 주문만 접근할 수 있습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ORDER_4041", description = "해당 주문은 존재하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ORDER_4091", description = "현재 주문 상태에서 결제할 수 없습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "PRODUCT_4091", description = "상품의 재고가 부족합니다.")
    })
    public ApiResponse<OrderResponseDto> pay(@PathVariable Long orderId) {
        return ApiResponse.onSuccess("주문 결제 성공",
                orderService.pay(currentMember.currentMemberId(), orderId));
    }

    @PatchMapping("/{orderId}/deliver")
    @Operation(summary = "주문 배송 완료 처리", description = "결제 완료된 주문을 배송 완료 상태로 전환합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_2000", description = "주문 배송 완료 처리 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ORDER_4031", description = "본인의 주문만 접근할 수 있습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ORDER_4041", description = "해당 주문은 존재하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ORDER_4091", description = "현재 주문 상태에서 배송 완료할 수 없습니다.")
    })
    public ApiResponse<OrderResponseDto> deliver(@PathVariable Long orderId) {
        return ApiResponse.onSuccess("주문 배송 완료 처리 성공",
                orderService.deliver(currentMember.currentMemberId(), orderId));
    }

    @PatchMapping("/{orderId}/cancel")
    @Operation(summary = "주문 취소",
            description = "주문을 취소합니다. 결제 완료 상태였다면 재고가 복구되며, 배송 완료된 주문은 취소할 수 없습니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_2000", description = "주문 취소 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ORDER_4031", description = "본인의 주문만 접근할 수 있습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ORDER_4041", description = "해당 주문은 존재하지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ORDER_4091", description = "이미 취소된 주문입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "ORDER_4092", description = "배송이 완료된 주문은 취소할 수 없습니다.")
    })
    public ApiResponse<OrderResponseDto> cancel(@PathVariable Long orderId) {
        return ApiResponse.onSuccess("주문 취소 성공",
                orderService.cancel(currentMember.currentMemberId(), orderId));
    }
}
