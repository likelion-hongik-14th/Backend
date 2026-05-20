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

    //기사작성
    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(@RequestBody ArticleCreateRequestDto requestDto) {
        ArticleResponseDto responseDto = articleService.create(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    //기사조회
    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDto> getArticle(@PathVariable Long articleId) {
        ArticleResponseDto responseDto = articleService.getArticle(articleId);
        return ResponseEntity.ok(responseDto);
    }
}