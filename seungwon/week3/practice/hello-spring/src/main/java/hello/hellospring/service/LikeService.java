package hello.hellospring.service;

import hello.hellospring.Entity.Article;
import hello.hellospring.Entity.Like;
import hello.hellospring.Entity.Member;
import hello.hellospring.repository.ArticleRepository;
import hello.hellospring.repository.LikeRepository;
import hello.hellospring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    public void pushLike(Long memberId, Long articleId) {
        // 1. 이미 좋아요를 눌렀는지 확인 (중복 방지)
        likeRepository.findByMemberAndArticle(memberId, articleId)
                .ifPresent(l -> { throw new IllegalArgumentException("이미 좋아요를 누른 기사입니다."); });

        // 2. 존재하는 회원과 기사인지 확인
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("기사가 존재하지 않습니다."));

        // 3. 좋아요 객체 생성 및 저장
        Like like = Like.builder()
                .member(member)
                .article(article)
                .build();

        likeRepository.save(like);
    }
}