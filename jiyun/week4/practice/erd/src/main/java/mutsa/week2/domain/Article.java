package mutsa.week2.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter // 필드 조회를 위해 필요
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "기사")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "기사아이디")
    private Long id;

    @Column(nullable = false, length = 40)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    // 과제 요구사항에 있던 외래키 필드들 (일단 Long으로 유지)
    @Column(name = "회원아이디")
    private Long memberId;

    @Column(name = "카테고리아이디")
    private Long categoryId;

    @Builder
    public Article(String title, String content, Long memberId, Long categoryId) {
        this.title = title;
        this.content = content;
        this.memberId = memberId;
        this.categoryId = categoryId;
    }
}