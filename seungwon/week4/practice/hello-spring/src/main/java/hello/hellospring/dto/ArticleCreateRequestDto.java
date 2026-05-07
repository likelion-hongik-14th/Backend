package hello.hellospring.dto;

import lombok.Getter;

@Getter
public class ArticleCreateRequestDto {
    // 필드명 깔끔하게 통일
    private String title;
    private String content;

    // URL 파라미터에서 DTO 내부로 이동
    private Long memberId;
    private String categoryName;
}