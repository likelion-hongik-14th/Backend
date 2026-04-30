package mutsa.session;

import lombok.Getter;

@Getter
public class ArticleCreateRequestDto {
    private String title;
    private String content;
    private Long memberId;
}
