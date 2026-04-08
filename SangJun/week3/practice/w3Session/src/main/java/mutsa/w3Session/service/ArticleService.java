package mutsa.w3Session.service;

import lombok.RequiredArgsConstructor;
import mutsa.w3Session.api.Article;
import mutsa.w3Session.dto.ArticleCreateRequestDTO;
import mutsa.w3Session.dto.ArticleResponseDTO;
import mutsa.w3Session.repository.ArticleRepository;
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