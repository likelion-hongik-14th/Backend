package mutsa.w3Session.dto;

import lombok.Getter;
@Getter
public class ArticleCreateRequestDto {
    private Long memberId;
    private Long categoryId;
    private String title;
    private String content;

}