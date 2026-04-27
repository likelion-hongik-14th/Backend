package mutsa.week4_practice.service;

import lombok.RequiredArgsConstructor;
import mutsa.week4_practice.domain.Article;
import mutsa.week4_practice.domain.Member;
import mutsa.week4_practice.dto.ArticleListResponseDto;
import mutsa.week4_practice.dto.ArticleRequestDto;
import mutsa.week4_practice.dto.ArticleResponseDto;
import mutsa.week4_practice.repository.ArticleRepository;
import mutsa.week4_practice.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    // 기사 작성
    public String createArticle(ArticleRequestDto requestDto) {
        // 회원 존재 여부 확인
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        // 기사 작성 로직
        Article article = Article.builder()
                .title(requestDto.getTitle())
                .categories(requestDto.getCategories())
                .content(requestDto.getContent())
                .author(member)
                .build();

        articleRepository.save(article);

        return "기사 작성 성공" + "기사 id = " + article.getId();
    }

    // 기사 조회
    public ArticleResponseDto getArticle(Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("해당 기사가 존재하지 않습니다."));

        return ArticleResponseDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .authorNickname(article.getAuthor().getNickname())
                .likeCount(article.getLikeCount())
                .viewCount(article.getViewCount())
                .build();
    }

    //기사 목록 조회
    public List<ArticleListResponseDto> getArticleList() {
        List<Article> articleList = articleRepository.findAll();

        //전체 기사 목록 stream, builder 형식으로 리스트로 생성해서 반환

        return articleList.stream()
                .map(article -> ArticleListResponseDto.builder()
                        .id(article.getId())
                        .title(article.getTitle())
                        .authorNickname(article.getAuthor().getNickname())
                        .viewCount(article.getViewCount())
                        .build())
                .toList();
    }
}
