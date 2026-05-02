package mutsa.practice.dto;

import lombok.Builder;
import lombok.Getter;
import mutsa.practice.domain.Article;

import java.time.LocalDateTime;

@Getter
public class ArticleResponseDto {

    private Long id;
    private String title;
    private String content;
    private String writerName;
    private LocalDateTime createdAt;

    @Builder
    public ArticleResponseDto(Long id, String title, String content, String writerName, LocalDateTime createdAt){
        this.id = id;
        this.title = title;
        this.content = content;
        this.writerName = writerName;
        this.createdAt = createdAt;
    }

    public static ArticleResponseDto from(Article article){
        return ArticleResponseDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .writerName(article.getMember().getName())
                .createdAt(article.getCreatedAt())
                .build();
    }

}
