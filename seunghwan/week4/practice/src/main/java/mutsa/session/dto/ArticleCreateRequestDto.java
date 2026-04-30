package mutsa.session.dto;

import lombok.Getter;

@Getter
public class ArticleCreateRequestDto {
    private String title;
    private String content;
    private Long member_id;
    private Long category_id;
}