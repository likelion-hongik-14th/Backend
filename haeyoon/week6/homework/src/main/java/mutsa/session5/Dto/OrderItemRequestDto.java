package mutsa.session5.Dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import mutsa.session5.Entity.Order;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class OrderItemRequestDto {
    private Long memberId;

    private Long orderId;
    @NotNull(message = "상품 ID는 필수입니다.")
    private Long productId;
    private String name;
    private Long orderPrice;
    @PositiveOrZero(message = "주문 수량은 0개 이상이어야 합니다.")
    private int orderQuantity;
    private Order.OrderStatus orderStatus;

    private Long addressId;
}
