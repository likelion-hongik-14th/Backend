package mutsa.session.service;


import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import mutsa.session.domain.Category;
import mutsa.session.domain.Member;
import mutsa.session.dto.ArticleCreateRequestDto;
import mutsa.session.dto.ArticleResponseDto;
import mutsa.session.domain.Article;
import mutsa.session.repository.ArticleRepository;
import mutsa.session.repository.CategoryRepository;
import mutsa.session.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    // 기사 작성 로직
    public ArticleResponseDto createArticle(ArticleCreateRequestDto requestDto) {
        // 1. 작성자(Member) 찾기
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 2. 카테고리(Category) 찾기
        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        // 3. 찾아온 객체들을 넣어 기사 완성하기
        Article article = new Article(requestDto.getTitle(), requestDto.getContent(), member, category);

        // 4. DB에 저장하고 응답 반환하기
        Article savedArticle = articleRepository.save(article);
        return new ArticleResponseDto(savedArticle);
    }

    @Transactional(readOnly = true)
    // 기사 단건 조회 로직
    public ArticleResponseDto getArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 기사가 없습니다. id = " + id));
        return new ArticleResponseDto(article);
    }
}