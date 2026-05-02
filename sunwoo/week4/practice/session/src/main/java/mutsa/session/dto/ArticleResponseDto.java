package mutsa.session.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mutsa.session.domain.Category;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class ArticleResponseDto {
    private Long id;
    private String author;
    private String title;
    private String content;
    private LocalDateTime date;
    private Category category;
}
