package org.example.shopping.dto.product;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
    private String name;
    private String description;

    @Min(value = 1, message = "가격은 최소 1원 이상이어야 합니다.")
    private Integer price;

    @Min(value = 1, message = "수량은 최소 1개 이상이어야 합니다.")
    private Integer stock;
}
