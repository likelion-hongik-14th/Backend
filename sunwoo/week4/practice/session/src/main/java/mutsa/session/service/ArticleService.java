package mutsa.session.service;

import lombok.RequiredArgsConstructor;
import mutsa.session.domain.Article;
import mutsa.session.dto.ArticleCreateRequestDto;
import mutsa.session.dto.ArticleResponseDto;
import mutsa.session.repository.ArticleRepository;
import mutsa.session.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
/*
@NoArgsConstructor 기본 생성자 (파라미터 없음)
@AllArgsConstructor 모든 필드 포함
@RequiredArgsConstructor 필수 필드만(final + @NonNull)
*/
public class ArticleService {

    private final ArticleRepository articleRepository; // DI
    private final MemberRepository memberRepository;//DI

    // 기사 작성 로직
    public String createArticle(ArticleCreateRequestDto requestDto) {
        Article article = Article.builder()
                .author(memberRepository.findById(requestDto.getMemberId()).orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + requestDto.getMemberId())))
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .date(LocalDateTime.now())
                .category(requestDto.getCategory())
                .build();
        articleRepository.save(article);
        return "기사 작성 완료 id: " + article.getId();
    }

    // 기사 단건 조회 로직
    public ArticleResponseDto getArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Article not found with id: " + id));

        return ArticleResponseDto.builder()
                .id(article.getId())
                .author(article.getAuthor().getName())
                .title(article.getTitle())
                .content(article.getContent())
                .date(article.getDate())
                .category(article.getCategory())
                .build();
    }

    public List<ArticleResponseDto> getAllArticles() {
        List<Article> articles = articleRepository.findAll();

        return articles.stream()
                .map(article -> ArticleResponseDto.builder()
                        .id(article.getId())
                        .author(article.getAuthor().getName())
                        .title(article.getTitle())
                        .content(article.getContent())
                        .date(article.getDate())
                        .category(article.getCategory())
                        .build())
                .toList();
    }
}
