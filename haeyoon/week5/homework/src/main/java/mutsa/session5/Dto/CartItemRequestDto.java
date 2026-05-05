package mutsa.session5.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemRequestDto {
    @JsonProperty("user_id")
    private Long userId;

    private Long cart_id;
    private Long product_id;
    private String name;
    private Long price;
    private int quantity;
}
