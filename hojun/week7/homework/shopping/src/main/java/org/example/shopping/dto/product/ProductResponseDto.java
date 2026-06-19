package org.example.shopping.dto.product;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponseDto {
    private Long productId;
    private String name;
    private String description;
    private Integer price;
    private Integer stock;
}
