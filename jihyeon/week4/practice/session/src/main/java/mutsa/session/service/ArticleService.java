package mutsa.session.service;

import lombok.RequiredArgsConstructor;
import mutsa.session.domain.Article;
import mutsa.session.domain.Member;
import mutsa.session.dto.ArticleCreateRequestDto;
import mutsa.session.dto.ArticleListResponseDto;
import mutsa.session.dto.ArticleResponseDto;
import mutsa.session.exception.ArticleNotFoundException;
import mutsa.session.exception.MemberNotFoundException;
import mutsa.session.repository.ArticleRepository;
import mutsa.session.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    // 기사 작성
    public ArticleResponseDto createArticle(ArticleCreateRequestDto requestDto) {

        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException("회원이 존재하지 않습니다."));

        Article article = new Article(
                null,
                requestDto.getTitle(),
                requestDto.getContent(),
                LocalDateTime.now(),
                member
        );

        Article saved = articleRepository.save(article);

        return new ArticleResponseDto(saved);
    }

    // 기사 단건 조회
    public ArticleResponseDto getArticle(Long id) {

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("해당 기사가 없습니다. id=" + id));

        return new ArticleResponseDto(article);
    }

    // 기사 목록 조회
    public List<ArticleListResponseDto> getArticleList() {
        return articleRepository.findAll()
                .stream()
                .map(ArticleListResponseDto::new)
                .toList();
    }
}