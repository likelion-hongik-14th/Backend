package mutsa.session;

import lombok.RequiredArgsConstructor;
import mutsa.session.domain.Article;
import mutsa.session.domain.Member;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;

    //기사 작성
    public ArticleResponseDto createArticle(ArticleCreateRequestDto requestDto) {

        //회원 조회
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));

        //기사 생성
        Article article = new Article(
                null,
                requestDto.getTitle(),
                requestDto.getContent(),
                LocalDateTime.now(),
                member
        );

        //저장
        Article saved = articleRepository.save(article);

        //DTO 변환
        return new ArticleResponseDto(saved);
    }

    //기사 단건 조회
    public ArticleResponseDto getArticle(Long id) {
         Article article = articleRepository.findById(id)
                 .orElseThrow(() -> new IllegalArgumentException("해당 기사가 없습니다. id=" + id));
         return new ArticleResponseDto(article);
    }

    //기사 목록 조회
    public List<ArticleListResponseDto> getArticleList() {
         return articleRepository.findAll()
                 .stream()
                 .map(ArticleListResponseDto::new)
                 .toList();
    }
}
