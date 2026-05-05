package mutsa.session5.Dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProductResponseDto {
    private Long product_id;
    private String name;
    private Long price;
    private int stock;
}
