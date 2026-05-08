package hello.hellospring.service;

import hello.hellospring.Entity.Article;
import hello.hellospring.Entity.Comment;
import hello.hellospring.Entity.Member;
import hello.hellospring.dto.CommentCreateRequestDto;
import hello.hellospring.repository.ArticleRepository;
import hello.hellospring.repository.CommentRepository;
import hello.hellospring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    // DTO를 매개변수로 받도록 수정
    public void createComment(CommentCreateRequestDto dto) {
        Article article = articleRepository.findById(dto.getArticleId())
                .orElseThrow(() -> new IllegalArgumentException("댓글을 달 기사가 존재하지 않습니다. id=" + dto.getArticleId()));

        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. id=" + dto.getMemberId()));

        Comment comment = Comment.builder()
                .content(dto.getContent()) // 변경된 필드명
                .createdAt(LocalDateTime.now()) // String -> LocalDateTime 적용
                .article(article)
                .member(member)
                .build();

        commentRepository.save(comment);
    }
}