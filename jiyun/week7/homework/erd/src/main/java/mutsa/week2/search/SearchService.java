package mutsa.week2.search;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mutsa.week2.global.apiPayload.exception.BusinessException;
import mutsa.week2.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

    private static final List<PopularSearchItemDto> POPULAR_KEYWORDS = List.of(
            new PopularSearchItemDto(1, "맥북 프로 M3", "UP"),
            new PopularSearchItemDto(2, "에어팟 에어", "STAY"),
            new PopularSearchItemDto(3, "갤럭시 S23", "DOWN")
    );

    private final ProductRepository productRepository;

    public SearchResponseDto searchByName(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            throw new BusinessException(SearchErrorCode.EMPTY_KEYWORD);
        }
        List<SearchItemDto> items = productRepository.findByNameContainingIgnoreCase(keyword).stream()
                .map(SearchItemDto::from)
                .toList();
        return SearchResponseDto.of(items);
    }

    public PopularSearchResponseDto getPopularKeywords() {
        return new PopularSearchResponseDto(LocalDateTime.now(), POPULAR_KEYWORDS);
    }
}
