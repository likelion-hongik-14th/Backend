package mutsa.w5homework.dto;

import lombok.Getter;

@Getter
public class CartItemCreateRequestDto {
    private Long memberId;
    private Long productId;
    private Long count;
}
