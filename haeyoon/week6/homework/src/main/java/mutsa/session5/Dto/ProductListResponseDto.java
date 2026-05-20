package mutsa.session5.Dto;

import lombok.*;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ProductListResponseDto {
    private List<ProductResponseDto> products;
}