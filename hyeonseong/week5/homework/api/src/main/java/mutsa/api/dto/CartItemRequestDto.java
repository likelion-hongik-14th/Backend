package mutsa.api.dto;

import lombok.Getter;

@Getter
public class CartItemRequestDto {
    private Long productId; //어떤 상품을
    private Integer quantity; //몇 개 담을지
}
