package mutsa.session;

import lombok.Getter;

@Getter
public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;

    // Entity를 DTO로 변환
    public ArticleResponseDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }

}
