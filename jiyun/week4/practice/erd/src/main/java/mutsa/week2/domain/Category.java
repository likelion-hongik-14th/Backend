package mutsa.week2.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "카테고리")
public class Category {
    @Id
    @Column(name = "카테고리아이디")
    private Long id;

    @Column(name = "제목", length = 50)
    private String title;

    @Builder
    public Category(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}