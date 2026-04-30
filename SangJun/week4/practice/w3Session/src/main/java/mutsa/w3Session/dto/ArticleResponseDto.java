package mutsa.w3Session.dto;


import lombok.Getter;
import mutsa.w3Session.entity.Article;

import java.time.LocalDateTime;

@Getter
public class ArticleResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public ArticleResponseDto(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createdAt = article.getCreatedAt();
    }
}