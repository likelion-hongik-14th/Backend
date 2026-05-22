package mutsa.session5.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class CartResponseDto {
    private Long cartId;
    private Long memberId;
    private List<CartItemResponseDto> cartItems;
    private Long totalPrice;
}