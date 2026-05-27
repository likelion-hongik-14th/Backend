package mutsa.mutsa_week5_hw.dto;

import lombok.Builder;
import lombok.Getter;
import mutsa.mutsa_week5_hw.domain.Product;

@Getter
@Builder // 수정
public class ProductResponseDto {

    //상품 조회
    private Long id;
    private String name;
    private int price;
    private int stock;

    public static ProductResponseDto from(Product product) { // 수정
        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
}
