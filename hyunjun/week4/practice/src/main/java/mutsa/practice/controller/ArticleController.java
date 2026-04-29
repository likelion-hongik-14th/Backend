package mutsa.practice.controller;

import lombok.RequiredArgsConstructor;
import mutsa.practice.dto.ArticleCreateRequestDto;
import mutsa.practice.dto.ArticleResponseDto;
import mutsa.practice.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.GetExchange;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    public ResponseEntity<Long> createArticle(@RequestBody ArticleCreateRequestDto requestDto) {

        Long articleId = articleService.createArticle(requestDto);
        return ResponseEntity.ok(articleId);
    }

    @GetMapping
    public ResponseEntity<ArticleResponseDto> getArticle(@PathVariable(name = "articleId") Long articleId){

        ArticleResponseDto responseDto = articleService.getArticle(articleId);
        return ResponseEntity.ok(responseDto);
    }
}
