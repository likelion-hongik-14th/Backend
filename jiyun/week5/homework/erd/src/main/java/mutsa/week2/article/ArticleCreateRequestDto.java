package mutsa.week2.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleCreateRequestDto {

    @NotBlank(message = "title은 필수입니다.")
    private String title;

    @NotBlank(message = "content는 필수입니다.")
    private String content;

    @NotNull(message = "memberId는 필수입니다.")
    private Long memberId;

    @NotNull(message = "categoryId는 필수입니다.")
    private Long categoryId;

    @Builder
    public ArticleCreateRequestDto(String title, String content, Long memberId, Long categoryId) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.categoryId = categoryId;
    }
}
