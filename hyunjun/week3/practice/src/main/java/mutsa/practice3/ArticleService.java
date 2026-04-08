package mutsa.practice3;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService{

    private final ArticleRepository articleRepository;

    public ArticleResponseDto createArticle(ArticleCreateRequestDto requestDto){
        Article article = new Article(requestDto.getTitle(), requestDto.getContent());
        Article savedArticle = articleRepository.save(article);
        return new ArticleResponseDto(savedArticle);
    }

    public ArticleResponseDto getArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 기사가 없습니다. id=" + id));
        return new ArticleResponseDto(article);
    }
    
}

