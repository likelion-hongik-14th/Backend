package mutsa.w5homework.dto;

import lombok.Getter;

@Getter
public class ProductCreateRequestDto {
    private String name;
    private String description;
    private Long price;
    private Long stock;
}
