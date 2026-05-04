package practice.session.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Transactional
    public ArticleResponseDto createArticle(ArticleRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Article article = Article.builder()
                .member(member)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .categories(requestDto.getCategories())
                .build();

        Article savedArticle = articleRepository.save(article);

        return ArticleResponseDto.builder()
                .id(savedArticle.getId())
                .title(savedArticle.getTitle())
                .content(savedArticle.getContent())
                .author(member.getName())
                .categories(savedArticle.getCategories())
                .build();
    }

    @Transactional(readOnly = true)
    public ArticleResponseDto getArticle(Long articleId){
        Article article = articleRepository.findById(articleId)
                .orElseThrow(()-> new IllegalArgumentException("해당 기사가 존재하지 않습니다."));

        return ArticleResponseDto.builder()
                .id(article.getId())
                .categories(article.getCategories())
                .title(article.getTitle())
                .content(article.getContent())
                .author(article.getMember().getName())
                .build();
    }

    @Transactional(readOnly = true)
    public List<ArticleListResponseDto> getArticleList(){
        List<Article> articleList = articleRepository.findAll();

        return articleList.stream()
                .map(article -> ArticleListResponseDto.builder()
                        .id(article.getId())
                        .categories(article.getCategories())
                        .title(article.getTitle())
                        .author(article.getMember().getName())
                        .build())
                .toList();
    }
}
