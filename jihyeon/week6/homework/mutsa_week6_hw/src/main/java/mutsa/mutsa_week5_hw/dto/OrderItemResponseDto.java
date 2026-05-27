package mutsa.mutsa_week5_hw.dto;

import lombok.Builder;
import lombok.Getter;
import mutsa.mutsa_week5_hw.domain.OrderItem;

// 주문 조회(응답) -> 주문 내역에 포함된 개별 상품의 주문 당시 이름, 가격, 수량을 표현
@Getter
@Builder
public class OrderItemResponseDto {

    private Long itemId;
    private String productName;
    private int orderPrice;
    private int quantity;
    private int subtotal;

    public static OrderItemResponseDto from(OrderItem item) {

        return OrderItemResponseDto.builder()
                .itemId(item.getId())
                .productName(item.getProductName())
                .orderPrice(item.getOrderPrice())
                .quantity(item.getQuantity())
                .subtotal(item.getOrderPrice() * item.getQuantity())
                .build();
    }

}
