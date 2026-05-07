package mutsa.w3Session.controller;

import lombok.RequiredArgsConstructor;
import mutsa.w3Session.dto.ArticleCreateRequestDto;
import mutsa.w3Session.dto.ArticleResponseDto;
import mutsa.w3Session.service.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    //1.기사 작성(POST)

    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(@RequestBody ArticleCreateRequestDto requestDTO){
        ArticleResponseDto responseDTO = articleService.createArticle(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    //2.기사 단건 조회(GET)
    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDto> getArticle(@PathVariable Long articleId){
        ArticleResponseDto responseDTO = articleService.getArticle(articleId);
        return ResponseEntity.ok(responseDTO);
    }
}