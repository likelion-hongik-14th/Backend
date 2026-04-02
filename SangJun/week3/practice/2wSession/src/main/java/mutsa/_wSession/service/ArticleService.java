package mutsa._wSession.service;

import lombok.RequiredArgsConstructor;
import mutsa._wSession.api.Article;
import mutsa._wSession.dto.ArticleCreateRequestDTO;
import mutsa._wSession.dto.ArticleResponseDTO;
import mutsa._wSession.repository.ArticleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService{

    private final ArticleRepository articleRepository;

    //기사 작성 로직
    public ArticleResponseDTO createArticle(ArticleCreateRequestDTO requestDTO){
        Article article = new Article(requestDTO.getTitle(), requestDTO.getContent());
        Article savedArticle = articleRepository.save(article);
        return new ArticleResponseDTO(savedArticle);
    }

    public ArticleResponseDTO getArticle(Long id){
        Article article = articleRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("해당 기사가 없습니다. " + id));
        return new ArticleResponseDTO(article);
    }
}