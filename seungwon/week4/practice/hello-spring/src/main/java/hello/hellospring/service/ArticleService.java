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
    private final ArticleCategoryRepository articleCategoryRepository; // 중간 테이블 리포지토리

    public ArticleResponseDto create(ArticleCreateRequestDto dto, Long memberId, String categoryName) {

        // 1. 회원 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("기사를 쓸 수 없습니다. 회원이 없습니다. id = " + memberId));

        // 2. 카테고리 확인 및 자동 생성 (핵심 로직)
        // DB에서 이름을 찾고, 없으면(orElseGet) 새 카테고리를 생성하여 저장합니다.
        Category category = categoryRepository.findByName(categoryName)
                .orElseGet(() -> categoryRepository.save(
                        Category.builder()
                                .categoryName(categoryName) // 필드명을 수정한 후의 이름
                                .build()
                ));

        // 3. 기사 생성 및 저장
        Article article = Article.builder()
                .articleContent(dto.getArticleContent())
                .articleTitle(dto.getArticleTitle())
                .articleDate(LocalDateTime.now())
                .member(member)
                .build();
        Article savedArticle = articleRepository.save(article);

        // 4. 기사와 카테고리 연결 (중간 테이블 저장)
        ArticleCategory articleCategory = ArticleCategory.builder()
                .article(savedArticle)
                .category(category)
                .build();
        articleCategoryRepository.save(articleCategory);

        return new ArticleResponseDto(savedArticle);
    }

    // 조회
    public ArticleResponseDto getArticle(Long id){
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 기사가 없습니다. id = " + id));
        return new ArticleResponseDto(article);
    }
}
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class ArticleService {
//    private final ArticleRepository articleRepository;
//    private final MemberRepository memberRepository;
/// /    //기사 작성 로직
/// /    public ArticleResponseDto createArticle(ArticleCreateRequestDto requestDto){
/// /        Article article = new Article(requestDto.getTitle(), requestDto.getContent());
/// /        Article savedArticle = articleRepository.save(article);
/// /        return new ArticleResponseDto(savedArticle);
/// /    }
/// /
/// /    //기사 단건 조회로직
//    public ArticleResponseDto getArticle(Long id){
//        Article article = articleRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("해당기사가 없습니다. id = "+id));
//        return new ArticleResponseDto(article);
//    }
//
//    //기사작성
//    public ArticleResponseDto create(ArticleCreateRequestDto dto, Long memberId) {
//
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(()-> new IllegalArgumentException("기사를 쓸 수 없습니다. id = " +memberId));
//
//        Article article = Article.builder()
//                .articleContent(dto.getArticleContent())
//                .articleTitle(dto.getArticleTitle())
//                .articleDate(LocalDateTime.now())
//                .member(member)
//                .build();
//        Article savedArticle = articleRepository.save(article);
//
//        // 저장된 엔티티를 응답용 DTO로 변환하여 반환
//        return new ArticleResponseDto(savedArticle);
//    }
//}
