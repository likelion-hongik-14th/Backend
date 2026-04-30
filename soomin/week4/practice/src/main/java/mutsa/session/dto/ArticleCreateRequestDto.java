package mutsa.session.dto;

import lombok.Getter;
import mutsa.session.domain.Category;

@Getter
public class ArticleCreateRequestDto {
    private String title;
    private String content;
    private Category category;
    private Long memberId;
}
