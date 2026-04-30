package hello.hellospring.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

//@Getter
//@Setter
//public class Article {
//    private Long id;
//    private String title;
//    private String content;
//
//    public Article(String title, String content) {
//        this.title = title;
//        this.content = content;
//    }
//}

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String articleTitle;
    private String articleContent;
    private LocalDateTime articleDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


}