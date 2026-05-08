package mutsa.session;

import lombok.Getter;

@Getter
public class ArticleCreateRequestDto {
    private Long memberId;
    private String title;
    private String content;

}
