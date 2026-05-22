package mutsa.shop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CartOrderRequestDto {
    private Long addressId;
    private List<Long> cartItemIds;
}
