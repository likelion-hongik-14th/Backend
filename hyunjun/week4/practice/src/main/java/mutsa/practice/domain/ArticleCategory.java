package mutsa.practice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(
        name = "article_category",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "article_category_rule",
                        columnNames = {"article_id", "category_id"}
                )
        }
)
public class ArticleCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public ArticleCategory(Article article, Category category){
        this.article = article;
        this.category = category;
    }
}
