package mutsa.session2;

import lombok.RequiredArgsConstructor;
import mutsa.session2.Entity.Article;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final SpringJpaArticleRepository articleRepository;

    // 기사 작성 로직
    @Transactional
    public ArticleResponseDto createArticle(ArticleCreateRequestDto requestDto) {
        Article article = new Article(requestDto.getTitle(), requestDto.getContent());
        Article savedArticle = articleRepository.save(article);
        return new ArticleResponseDto(savedArticle);
    }

    // 기사 단건 조회 로직
    public ArticleResponseDto getArticle(Long Id) {
        Article article = articleRepository.findById(Id)
                .orElseThrow(() -> new IllegalArgumentException("해당 기사가 없습니다. id=" + id));
        return new ArticleResponseDto(article);
    }
}