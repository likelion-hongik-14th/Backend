package hello.hellospring.dto;

import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class ArticleCreateRequestDto {
    private String articleTitle;
    private String articleContent;
//    private LocalDateTime articleDate;
}
