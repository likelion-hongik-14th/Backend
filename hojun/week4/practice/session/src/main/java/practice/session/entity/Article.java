package practice.session.entity;

import jakarta.persistence.*;
import lombok.*;
import practice.session.entity.enums.Category;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private String title;
    private String content;
    private LocalDateTime date;

    @ElementCollection(targetClass = Category.class)
    @CollectionTable(name = "article_category", joinColumns = @JoinColumn(name = "article_id"))
    @Enumerated(EnumType.STRING)
    private List<Category> categories = new ArrayList<>();

}
