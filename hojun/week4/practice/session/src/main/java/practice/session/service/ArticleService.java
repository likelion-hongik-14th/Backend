package practice.session.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.session.dto.ArticleListResponseDto;
import practice.session.dto.ArticleRequestDto;
import practice.session.dto.ArticleResponseDto;
import practice.session.entity.Article;
import practice.session.entity.Member;
import practice.session.repository.ArticleRepository;
import practice.session.repository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    public String createArticle(ArticleRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        Article article = Article.builder()
                .title(requestDto.getTitle())
                .categories(requestDto.getCategories())
                .content(requestDto.getContent())
                .author(member)
                .build();
        articleRepository.save(article);

        return "기사 작성 성공" + "기사 id = " + article.getId();
    }

    public ArticleResponseDto getArticle(Long articleId){
        Article article = articleRepository.findById(articleId)
                .orElseThrow(()-> new IllegalArgumentException("해당 기사가 존재하지 않습니다."));

        return ArticleResponseDto.builder()
                .id(article.getId())
                .categories(article.getCategories())
                .title(article.getTitle())
                .content(article.getContent())
                .author(article.getAuthor().getName())
                .build();
    }

    public List<ArticleListResponseDto> getArticleList(){
        List<Article> articleList = articleRepository.findAll();

        return articleList.stream()
                .map(article -> ArticleListResponseDto.builder()
                        .id(article.getId())
                        .categories(article.getCategories())
                        .title(article.getTitle())
                        .author(article.getAuthor().getName())
                        .build())
                .toList();
    }
}
