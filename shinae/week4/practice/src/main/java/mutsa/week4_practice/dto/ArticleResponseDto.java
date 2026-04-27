package mutsa.week4_practice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mutsa.week4_practice.domain.enums.Category;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponseDto {
    private Long id;
    private List<Category> categories;
    private String title;
    private String content;
    private String authorNickname;
    private int likeCount;
    private int viewCount;
}
