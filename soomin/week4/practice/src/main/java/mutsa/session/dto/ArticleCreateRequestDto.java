package mutsa.session.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import mutsa.session.domain.Category;
import jakarta.validation.constraints.NotNull;

@Getter
public class ArticleCreateRequestDto {

    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    @NotNull(message = "카테고리를 선택해주세요.")
    private Category category;

    @NotNull(message = "작성자 ID는 필수입니다.")
    private Long memberId;
}
