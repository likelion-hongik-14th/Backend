package mutsa.api.dto;

import lombok.Getter;

@Getter
public class OrderRequestDto {
    private Long productId;
    private int count;
    private Long addressId;
}
