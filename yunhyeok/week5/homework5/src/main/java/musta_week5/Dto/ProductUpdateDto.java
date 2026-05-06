package musta_week5.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductUpdateDto {

    private String productName;
    private Integer price;
    private Long categoryId;

}
