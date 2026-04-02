package mutsa._wSession.controller;

import lombok.RequiredArgsConstructor;
import mutsa._wSession.dto.ArticleCreateRequestDTO;
import mutsa._wSession.dto.ArticleResponseDTO;
import mutsa._wSession.service.ArticleService;
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
    public ResponseEntity<ArticleResponseDTO> createArticle(@RequestBody ArticleCreateRequestDTO requestDTO){
        ArticleResponseDTO responseDTO = articleService.createArticle(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    //2.기사 단건 조회(GET)
    @GetMapping("/{articleId}")
    public ResponseEntity<ArticleResponseDTO> getArticle(@PathVariable Long articleId){
        ArticleResponseDTO responseDTO = articleService.getArticle(articleId);
        return ResponseEntity.ok(responseDTO);
    }
}