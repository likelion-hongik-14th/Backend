package hello.hellospring.dto;

import lombok.Getter;

@Getter
public class ArticleCreateRequestDto {
    private String title;
    private String content;
}
