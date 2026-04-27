package mutsa.week4_practice.dto;

import lombok.Getter;
import mutsa.week4_practice.domain.enums.Category;

import java.util.List;

@Getter
public class ArticleRequestDto {
    private Long memberId;
    private String title;
    private String content;
    private List<Category> categories;
}
