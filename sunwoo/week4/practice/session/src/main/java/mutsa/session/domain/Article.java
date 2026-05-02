package mutsa.session.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member author;

    private String title;

    @Lob //Large Object
    private String content;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    //뉴스 카테고리는 고정이므로 열거형을 선택함, Ordinal을 사용하면 카테고리 추가 시 기존 데이터가 꼬일 수 있음
    private Category category;
}
