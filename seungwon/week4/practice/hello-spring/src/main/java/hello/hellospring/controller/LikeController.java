package hello.hellospring.controller;

import hello.hellospring.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    // 좋아요 누르기: POST http://localhost:8080/api/v1/likes?memberId=1&articleId=1
    @PostMapping
    public ResponseEntity<String> pushLike(
            @RequestParam Long memberId,
            @RequestParam Long articleId) {

        likeService.pushLike(memberId, articleId);
        return ResponseEntity.ok("좋아요 완료!");
    }
}