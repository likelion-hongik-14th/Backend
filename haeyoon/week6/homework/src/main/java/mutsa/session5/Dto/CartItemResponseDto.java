package mutsa.session5.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class CartItemResponseDto {
    private Long cartId;
    private Long productId;
    private String name;
    private Long price;
    private int quantity;
    private Long totalPrice;
}
