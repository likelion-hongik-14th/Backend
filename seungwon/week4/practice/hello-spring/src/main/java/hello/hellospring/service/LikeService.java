package hello.hellospring.service;

import hello.hellospring.Entity.Article;
import hello.hellospring.Entity.Like;
import hello.hellospring.Entity.Member;
import hello.hellospring.dto.LikeRequestDto;
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

    public void pushLike(LikeRequestDto dto) {
        // JPA 네이밍 룰에 맞게 수정
        likeRepository.findByMemberIdAndArticleId(dto.getMemberId(), dto.getArticleId())
                .ifPresent(l -> { throw new IllegalArgumentException("이미 좋아요를 누른 기사입니다."); });

        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
        Article article = articleRepository.findById(dto.getArticleId())
                .orElseThrow(() -> new IllegalArgumentException("기사가 존재하지 않습니다."));

        Like like = Like.builder()
                .member(member)
                .article(article)
                .build();

        likeRepository.save(like);
    }
}