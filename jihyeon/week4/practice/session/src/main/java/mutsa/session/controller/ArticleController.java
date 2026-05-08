package mutsa.session.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.session.dto.ArticleCreateRequestDto;
import mutsa.session.dto.ArticleListResponseDto;
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
    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(
            @RequestBody @Valid ArticleCreateRequestDto requestDto) {

        ArticleResponseDto responseDto = articleService.createArticle(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 2. 기사 단건 조회 (GET)
    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDto> getArticle(
            @PathVariable Long articleId) {
        return ResponseEntity.ok(articleService.getArticle(articleId));
    }

    @GetMapping
    public ResponseEntity<List<ArticleListResponseDto>> getArticleList() {
        return ResponseEntity.ok(articleService.getArticleList());
    }

}
