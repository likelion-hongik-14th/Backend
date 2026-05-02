package practice.session.dto;

import lombok.Getter;
import practice.session.entity.enums.Category;

import java.util.List;

@Getter
public class ArticleRequestDto {
    private Long memberId;
    private String title;
    private String content;
    private List<Category> categories;
}
