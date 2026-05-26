package mutsa.week2.product;

import java.util.List;
import org.springframework.data.domain.Page;

public record ProductListResponseDto(
        List<ProductResponseDto> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean last
) {
    public static ProductListResponseDto from(Page<ProductResponseDto> page) {
        return new ProductListResponseDto(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
}
