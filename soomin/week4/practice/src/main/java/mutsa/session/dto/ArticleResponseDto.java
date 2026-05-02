package mutsa.session.dto;

import lombok.Getter;
import mutsa.session.domain.Article;
import mutsa.session.domain.Category;

import java.time.LocalDateTime;

@Getter
public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;
    private Category category;
    private LocalDateTime date;
    private String memberName;

    public ArticleResponseDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.category = article.getCategory();
        this.date = article.getDate();
        this.memberName = article.getMember().getName();
    }
}
