package hello.hellospring.service;

import hello.hellospring.Entity.Article;
import hello.hellospring.Entity.Comment;
import hello.hellospring.Entity.Member;
import hello.hellospring.repository.ArticleRepository;
import hello.hellospring.repository.CommentRepository;
import hello.hellospring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public void createComment(Long articleId, Long memberId, String content) {
        // 1. 존재하는 기사인지 확인
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 달 기사가 존재하지 않습니다. id=" + articleId));

        // 2. 존재하는 회원인지 확인
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. id=" + memberId));

        // 3. 날짜를 String으로 변환 (엔티티 타입에 맞춤)
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 4. 엔티티 빌드 (제시해주신 필드명 그대로 사용)
        Comment comment = Comment.builder()
                .comment_content(content)
                .comment_date(now)
                .article(article)
                .member(member)
                .build();

        commentRepository.save(comment);
    }
}
