package mutsa.session5.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import mutsa.session5.Entity.Order;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class OrderResponseDto {
    private Long orderId;
    private String orderNumber;
    private LocalDateTime orderDate;
    private Order.OrderStatus orderStatus;

    private String baseAddress;
    private String detailAddress;

    private List<OrderItemResponseDto> orderItems;

    private Long totalPrice;
}
