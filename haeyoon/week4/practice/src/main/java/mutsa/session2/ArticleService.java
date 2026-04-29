package mutsa.session2;

import lombok.RequiredArgsConstructor;
import mutsa.session2.Entity.Article;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final SpringJpaArticleRepository articleRepository;

    // 기사 작성 로직
    @Transactional
    public ArticleResponseDto createArticle(ArticleCreateRequestDto requestDto) {
        Member member = memberRepository.findById(requestDte.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Article article = Article.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .member(member)
                .date(LocalDateTime.now())
                .build();

        Article savedArticle = articleRepository.save(article);
        return new ArticleResponseDto(savedArticle);
    }

    // 기사 단건 조회 로직
    public ArticleResponseDto getArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 기사가 없습니다. id=" + id));

        return ArticleResponseDto.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .build();
    }
}