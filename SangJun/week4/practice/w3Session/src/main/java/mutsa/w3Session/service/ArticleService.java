package mutsa.w3Session.service;

import lombok.RequiredArgsConstructor;
import mutsa.w3Session.entity.Article;
import mutsa.w3Session.dto.ArticleCreateRequestDto;
import mutsa.w3Session.dto.ArticleResponseDto;
import mutsa.w3Session.entity.Category;
import mutsa.w3Session.entity.Member;
import mutsa.w3Session.repository.ArticleRepository;
import mutsa.w3Session.repository.CategoryRepository;
import mutsa.w3Session.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService{

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    //기사 작성 로직
    @Transactional
    public ArticleResponseDto createArticle(ArticleCreateRequestDto requestDTO){
        Member member = memberRepository.findById(requestDTO.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버."));

        Category category = categoryRepository.findById(requestDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리."));

        Article article = Article.builder()
                .member(member)
                .category(category)
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