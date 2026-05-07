package mutsa.session.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import mutsa.session.domain.Category;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ArticleCreateRequestDto {
    private Long memberId;
    private String title;
    private String content;
    private Category category;
}
