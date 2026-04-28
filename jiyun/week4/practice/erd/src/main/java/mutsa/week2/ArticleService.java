package mutsa.week2;

import lombok.RequiredArgsConstructor;
import mutsa.week2.domain.Article;
import mutsa.week2.ArticleCreateRequestDto;
import mutsa.week2.ArticleResponseDto;
import mutsa.week2.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional
    public ArticleResponseDto createArticle(ArticleCreateRequestDto requestDto){
        Article article = Article.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();

        Article savedArticle = articleRepository.save(article);
        return new ArticleResponseDto(savedArticle);
    }

    public ArticleResponseDto getArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 기사가 없습니다. id=" + id));
        return new ArticleResponseDto(article);
    }
}