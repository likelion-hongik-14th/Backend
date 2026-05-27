package mutsa.week2.search;

import java.time.LocalDateTime;
import java.util.List;

public record PopularSearchResponseDto(LocalDateTime updatedAt, List<PopularSearchItemDto> items) {
}
