package hello.hellospring.dto;

import lombok.Getter;

@Getter
public class LikeRequestDto {
    private Long memberId;
    private Long articleId;
}