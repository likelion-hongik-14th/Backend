package mutsa.session.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import mutsa.session.domain.Category;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class ArticleListResponseDto {
    private Long id;
    private String author;
    private String title;
    private LocalDateTime date;
    private Category category;
}
