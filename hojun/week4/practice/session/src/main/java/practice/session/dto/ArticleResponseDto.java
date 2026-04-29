package practice.session.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import practice.session.entity.Article;
import practice.session.entity.enums.Category;

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
    private String author;
}
