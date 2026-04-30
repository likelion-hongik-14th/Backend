package mutsa.w3Session.service;

import lombok.RequiredArgsConstructor;
import mutsa.w3Session.entity.Article;
import mutsa.w3Session.dto.ArticleCreateRequestDto;
import mutsa.w3Session.dto.ArticleResponseDto;
import mutsa.w3Session.repository.JpaArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService{

    private final JpaArticleRepository articleRepository;

    //기사 작성 로직
    @Transactional
    public ArticleResponseDto createArticle(ArticleCreateRequestDto requestDTO){
        Article article = Article.builder()
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .build();

        Article savedArticle = articleRepository.save(article);
        return new ArticleResponseDto(savedArticle);
    }

    @Transactional(readOnly = true)
    public ArticleResponseDto getArticle(Long id){
        Article article = articleRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("해당 기사가 없습니다. " + id));
        return new ArticleResponseDto(article);
    }
}