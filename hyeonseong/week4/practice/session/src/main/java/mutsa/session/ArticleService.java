package mutsa.session;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository; // 의존성 주입 (DI)
    private final MemberRepository memberRepository;

    // 기사 작성 로직
    @Transactional
    public ArticleResponseDto createArticle(ArticleCreateRequestDto requestDto){

        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다. id = " + requestDto.getMemberId()));

        Article article = new Article(requestDto.getTitle(), requestDto.getContent(), member);

        Article savedArticle = articleRepository.save(article);
        return new ArticleResponseDto(savedArticle);
    }

    // 기사 단건 조회 로직
    public ArticleResponseDto getArticle(Long id){

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 기사가 없습니다. id = "+id));

        return new ArticleResponseDto(article);
    }

}
