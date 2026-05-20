package mutsa.session5.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class OrderItemResponseDto {
    private Long productId;

    private Long orderId;
    private String name;
    private Long orderPrice;
    private int orderQuantity;
    private LocalDateTime orderDate;
    private Long totalPrice;
}
