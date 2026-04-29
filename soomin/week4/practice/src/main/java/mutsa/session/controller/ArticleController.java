package mutsa.session.controller;

import lombok.RequiredArgsConstructor;
import mutsa.session.dto.ArticleCreateRequestDto;
import mutsa.session.dto.ArticleResponseDto;
import mutsa.session.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(@RequestBody ArticleCreateRequestDto requestDto) {
        ArticleResponseDto responseDto = articleService.createArticle(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDto> getArticle(@PathVariable Long articleId) {
        ArticleResponseDto responseDto = articleService.getArticle(articleId);
        return ResponseEntity.ok(responseDto);
    }
}
