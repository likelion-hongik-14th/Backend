package mutsa.hw5.dto.product;

import lombok.Builder;
import lombok.Getter;
import mutsa.hw5.domain.Product;

@Getter
@Builder
public class ProductResponseDto {
    private Long productId;
    private String productName;
    private Long productPrice;
    private Long productStock;

    // Entity → DTO 변환
    public static ProductResponseDto from(Product product) {
        return ProductResponseDto.builder() // 메서드 체이닝으로 객체를 쉽게 반환
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .productStock(product.getProductStock())
                .build();
    }
}
