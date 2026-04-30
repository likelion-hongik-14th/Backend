package hello.hellospring.controller;

import hello.hellospring.dto.ArticleCreateRequestDto;
import hello.hellospring.dto.ArticleResponseDto;
import hello.hellospring.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    // 1. 기사 작성 로직 수정 (memberId를 추가로 받음)
    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(
            @RequestBody ArticleCreateRequestDto requestDto,
            @RequestParam Long memberId,
            @RequestParam String categoryName) {

        ArticleResponseDto responseDto = articleService.create(requestDto, memberId, categoryName);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 2. 기사 단건 조회
    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDto> getArticle(@PathVariable Long articleId) {
        // 서비스에 getArticle 메서드의 주석을 해제해야 동작합니다!
        ArticleResponseDto responseDto = articleService.getArticle(articleId);
        return ResponseEntity.ok(responseDto);
    }

}