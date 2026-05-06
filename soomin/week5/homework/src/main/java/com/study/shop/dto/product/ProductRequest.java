package com.study.shop.dto.product;

import com.study.shop.domain.ProductStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProductRequest {

    @NotNull(message = "카테고리 ID는 필수입니다.")
    private Long categoryId;

    @NotBlank(message = "상품명은 필수입니다.")
    private String name;

    @Min(value = 1, message = "상품 가격은 1원 이상이어야 합니다.")
    private int price;

    @Min(value = 0, message = "상품 재고는 0개 이상이어야 합니다.")
    private int stockQuantity;

    private String description;

    @NotNull(message = "상품 상태는 필수입니다.")
    private ProductStatus status;
}
