package mutsa.session;

import lombok.Getter;

@Getter
public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;

    public ArticleResponseDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
