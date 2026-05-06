package mutsa.week2.category;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "카테고리")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "카테고리아이디")
    private Long id;

    @Column(name = "제목", length = 50)
    private String title;

    @Builder
    public Category(String title) {
        this.title = title;
    }
}
