package mutsa.session.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleCreateRequestDto {
    private String title;
    private String content;
    private Long memberId;
}
