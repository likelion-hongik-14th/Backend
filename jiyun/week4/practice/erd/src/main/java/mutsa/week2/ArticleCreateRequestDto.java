package mutsa.week2;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleCreateRequestDto {
    private String title;
    private String content;

    @Builder // 테스트나 코드 내 생성을 위해 추가
    public ArticleCreateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}