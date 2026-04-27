package mutsa.week4_practice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mutsa.week4_practice.domain.enums.Category;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ArticleListResponseDto {
    private Long id;
    private List<Category> categories;
    private String title;
    private String authorNickname;
    private int viewCount;
}
