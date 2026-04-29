package mutsa.session.service;


import mutsa.session.domain.Category;
import mutsa.session.domain.Member;
import mutsa.session.dto.ArticleCreateRequestDto;
import mutsa.session.dto.ArticleResponseDto;
import mutsa.session.domain.Article;
import mutsa.session.repository.SpringDataJpaArticleRepository;
import mutsa.session.repository.SpringDataJpaCategoryRepository;
import mutsa.session.repository.SpringDataJpaMemberRepository;
import org.springframework.stereotype.Service;

/*
@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    //기사 작성 로직
    public ArticleResponseDto createArticle(ArticleCreateRequestDto requestDto) {
        Article article = new Article(requestDto.getTitle(), requestDto.getContent());
        Article savedArticle = articleRepository.save(article);
        return new ArticleResponseDto(savedArticle);
    }

    // 기사 단건 조회 로직
    public ArticleResponseDto getArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 기사가 없습니다. id = " + id));
        return new ArticleResponseDto(article);
    }
}
*/

@Service
public class ArticleService {
    // [수정] 타입을 새로 만든 인터페이스인 SpringDataJpaArticleRepository로 변경합니다.
    private final SpringDataJpaArticleRepository articleRepository;
    private final SpringDataJpaMemberRepository memberRepository;
    private final SpringDataJpaCategoryRepository categoryRepository;

    // @RequiredArgsConstructor 대신 직접 만든 생성자
    public ArticleService(SpringDataJpaArticleRepository articleRepository,
                          SpringDataJpaMemberRepository memberRepository,
                          SpringDataJpaCategoryRepository categoryRepository) {
        this.articleRepository = articleRepository;
        this.memberRepository = memberRepository;
        this.categoryRepository = categoryRepository;
    }

    // 기사 작성 로직
    public ArticleResponseDto createArticle(ArticleCreateRequestDto requestDto) {
        // 1. 작성자(Member) 찾기
        Member member = memberRepository.findById(requestDto.getMember_id())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 2. 카테고리(Category) 찾기
        Category category = categoryRepository.findById(requestDto.getCategory_id())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        // 3. 찾아온 객체들을 넣어 기사 완성하기
        Article article = new Article(requestDto.getTitle(), requestDto.getContent(), member, category);

        // 4. DB에 저장하고 응답 반환하기
        Article savedArticle = articleRepository.save(article);
        return new ArticleResponseDto(savedArticle);
    }

    // 기사 단건 조회 로직
    public ArticleResponseDto getArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 기사가 없습니다. id = " + id));
        return new ArticleResponseDto(article);
    }
}