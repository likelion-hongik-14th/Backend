package mutsa.week2.article;

import lombok.RequiredArgsConstructor;
import mutsa.week2.category.Category;
import mutsa.week2.category.CategoryRepository;
import mutsa.week2.common.error.BusinessException;
import mutsa.week2.common.error.ErrorCode;
import mutsa.week2.member.Member;
import mutsa.week2.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ArticleResponseDto createArticle(ArticleCreateRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
        Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));

        Article article = Article.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .member(member)
                .category(category)
                .build();

        return ArticleResponseDto.from(articleRepository.save(article));
    }

    public ArticleResponseDto getArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND));
        return ArticleResponseDto.from(article);
    }
}
