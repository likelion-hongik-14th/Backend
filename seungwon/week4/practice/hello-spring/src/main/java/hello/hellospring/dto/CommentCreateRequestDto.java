package hello.hellospring.dto;

import lombok.Getter;

@Getter
public class CommentCreateRequestDto {
    private Long articleId;
    private Long memberId;
    private String content;
}