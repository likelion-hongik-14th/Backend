package mutsa.week4_practice.controller;

import lombok.RequiredArgsConstructor;
import mutsa.week4_practice.dto.ArticleListResponseDto;
import mutsa.week4_practice.dto.ArticleRequestDto;
import mutsa.week4_practice.dto.ArticleResponseDto;
import mutsa.week4_practice.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    //기사 작성
    @PostMapping("/new")
    public String createArticle(@RequestBody ArticleRequestDto requestDto) {
        return articleService.createArticle(requestDto);
    }

    //기사 단건
    @GetMapping("/{articleId}")
    public ArticleResponseDto getArticle(@PathVariable Long articleId) {
        return articleService.getArticle(articleId);}

    @GetMapping("/list")
    public List<ArticleListResponseDto> getArticleList() {
        return articleService.getArticleList();
    }
}
