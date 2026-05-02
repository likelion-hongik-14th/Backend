package musta.session.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Comment_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Article_id", nullable = false)
    private Article article;

    private String detail;
    private String day;



}
