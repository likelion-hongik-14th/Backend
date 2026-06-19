package mutsa.hw5.dto.order;

import lombok.Builder;
import lombok.Getter;
import mutsa.hw5.domain.OrderItem;

@Getter
@Builder
public class OrderItemResponseDto {

    private String productName;
    private Long productPrice;
    private Long itemQuantity;

    // Entity → DTO 변환
    // Service에서 호출하지만, 변환 로직을 DTO에 숨겨두어서 깔끔하게 유지
    public static OrderItemResponseDto from(OrderItem orderItem) {
        return OrderItemResponseDto.builder()
                .productName(orderItem.getProductName())
                .productPrice(orderItem.getProductPrice())
                .itemQuantity(orderItem.getItemQuantity())
                .build();
    }
}
