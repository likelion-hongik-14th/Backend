package mutsa.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProductRequestDto {
    private String name;
    private int price;
    private int stock;
    private String description;
}
