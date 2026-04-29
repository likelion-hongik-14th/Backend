package mutsa.session2.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter

public class ArticleCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="article_id", nullable = false)
    private Article article;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="category_id", nullable = false)
    private Category category;
}
