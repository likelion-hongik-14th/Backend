package mutsa.week2.order;

import java.util.List;

public record OrderListResponseDto(
        List<OrderResponseDto> orders
) {
    public static OrderListResponseDto of(List<OrderResponseDto> orders) {
        return new OrderListResponseDto(orders);
    }
}
