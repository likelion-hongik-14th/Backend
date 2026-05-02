package mutsa.session.controller;

import lombok.RequiredArgsConstructor;
import mutsa.session.dto.ArticleCreateRequestDto;
import mutsa.session.dto.ArticleResponseDto;
import mutsa.session.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    // 1. 기사 작성 (POST)
    @PostMapping("/new")
    public ResponseEntity<String> createArticle(@RequestBody ArticleCreateRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(articleService.createArticle(requestDto));
    }

    // 2. 기사 단건 조회 (GET)
    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDto> getArticle(@PathVariable Long articleId) {
        return ResponseEntity.ok(articleService.getArticle(articleId));
    }

    // 기사 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<ArticleResponseDto>> getAllArticles() {
        return ResponseEntity.ok(articleService.getAllArticles());
    }
}