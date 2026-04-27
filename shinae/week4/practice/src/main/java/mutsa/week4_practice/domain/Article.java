package mutsa.week4_practice.domain;

import jakarta.persistence.*;
import lombok.*;
import mutsa.week4_practice.domain.enums.Category;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Article extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member author;

    private String title;

    @Lob
    private String content;

    private int viewCount;
    private int likeCount;

    @ElementCollection(targetClass = Category.class)
    @CollectionTable(name = "article_category", joinColumns = @JoinColumn(name = "article_id"))
    @Enumerated(EnumType.STRING)
    private List<Category> categories = new ArrayList<>();
}
