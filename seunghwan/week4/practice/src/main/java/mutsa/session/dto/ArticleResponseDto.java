package mutsa.session.dto;

import lombok.Getter;
import mutsa.session.domain.Article;

@Getter
public class ArticleResponseDto {
    private Long article_id;
    private String title;
    private String content;
    private Long member_id;
    private Long category_id;

    // Entity를 DTO로 변환
    public ArticleResponseDto(Article article) {
        this.article_id = article.getArticle_id();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.member_id = article.getMember().getMember_id();
        this.category_id = article.getCategory().getCategory_id();
    }
}