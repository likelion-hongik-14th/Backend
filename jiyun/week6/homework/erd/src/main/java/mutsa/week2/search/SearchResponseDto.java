package mutsa.week2.search;

import java.util.List;

public record SearchResponseDto(int totalCount, List<SearchItemDto> items) {
    public static SearchResponseDto of(List<SearchItemDto> items) {
        return new SearchResponseDto(items.size(), items);
    }
}
