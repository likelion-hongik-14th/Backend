package mutsa.practice.service;

import lombok.RequiredArgsConstructor;
import mutsa.practice.domain.Article;
import mutsa.practice.domain.ArticleCategory;
import mutsa.practice.domain.Category;
import mutsa.practice.domain.Member;
import mutsa.practice.dto.ArticleCreateRequestDto;
import mutsa.practice.dto.ArticleResponseDto;
import mutsa.practice.repository.ArticleRepository;
import mutsa.practice.repository.CategoryRepository;
import mutsa.practice.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public long createArticle(ArticleCreateRequestDto requestDto){

        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        Article article = Article.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .member(member)
                .build();

        ArticleCategory articleCategory = ArticleCategory.builder()
                .article(article)
                .category(category)
                .build();

        article.getArticleCategories().add(articleCategory);

        return articleRepository.save(article).getId();
    }

    public ArticleResponseDto getArticle(Long articleId){

        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

        return ArticleResponseDto.from(article);

    }
}
