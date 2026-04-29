package mutsa.session.service;

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
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public ArticleResponseDto createArticle(ArticleCreateRequestDto requestDto) {

        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니. id=" + requestDto.getMemberId()));

        Article article = new Article(
                requestDto.getTitle(),
                requestDto.getContent(),
                member,
                requestDto.getCategory()
        );

        Article savedArticle = articleRepository.save(article);
        return new ArticleResponseDto(savedArticle);
    }

    public ArticleResponseDto getArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 기사가 없습니다. id=" + id));
        return new ArticleResponseDto(article);
    }
}
