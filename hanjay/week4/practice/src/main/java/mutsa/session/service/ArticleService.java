package mutsa.session.service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import mutsa.session.domain.Article;
import mutsa.session.domain.Member;
import mutsa.session.dto.ArticleCreateRequestDto;
import mutsa.session.dto.ArticleResponseDto;
import mutsa.session.repository.ArticleRepository;
import mutsa.session.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    // 기사 작성 로직
    public ArticleResponseDto createArticle(ArticleCreateRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Article article = Article.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .member(member)
                .date(System.currentTimeMillis())
                .build();

        Article savedArticle = articleRepository.save(article);
        return new ArticleResponseDto(savedArticle);
    }

    // 기사 단건 조회 로직
    @Transactional(readOnly = true)
    public ArticleResponseDto getArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 기사가 없습니다. id=" + id));
        return new ArticleResponseDto(article);
    }
}
