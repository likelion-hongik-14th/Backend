package hello.hellospring.service;

import hello.hellospring.Entity.Article;
import hello.hellospring.Entity.ArticleCategory;
import hello.hellospring.Entity.Category;
import hello.hellospring.Entity.Member;
import hello.hellospring.dto.ArticleCreateRequestDto;
import hello.hellospring.dto.ArticleResponseDto;
import hello.hellospring.repository.ArticleCategoryRepository;
import hello.hellospring.repository.ArticleRepository;
import hello.hellospring.repository.CategoryRepository;
import hello.hellospring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final ArticleCategoryRepository articleCategoryRepository;

    public ArticleResponseDto create(ArticleCreateRequestDto dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("기사를 쓸 수 없습니다. 회원이 없습니다. id = " + dto.getMemberId()));

        Category category = categoryRepository.findByCategoryName(dto.getCategoryName())
                .orElseGet(() -> categoryRepository.save(
                        Category.builder()
                                .categoryName(dto.getCategoryName())
                                .build()
                ));

        Article article = Article.builder()
                .title(dto.getTitle()) // 변경된 필드명
                .content(dto.getContent()) // 변경된 필드명
                .articleDate(LocalDateTime.now())
                .member(member)
                .build();
        Article savedArticle = articleRepository.save(article);

        ArticleCategory articleCategory = ArticleCategory.builder()
                .article(savedArticle)
                .category(category)
                .build();
        articleCategoryRepository.save(articleCategory);

        // 리뷰 반영: 정적 팩토리 메서드로 반환
        return ArticleResponseDto.from(savedArticle);
    }

    public ArticleResponseDto getArticle(Long id){
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 기사가 없습니다. id = " + id));
        return ArticleResponseDto.from(article);
    }
}