package hello.hellospring.controller;

import hello.hellospring.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성: POST http://localhost:8080/api/v1/comments?articleId=1&memberId=1&content=내용
    @PostMapping
    public ResponseEntity<String> saveComment(
            @RequestParam Long articleId,
            @RequestParam Long memberId,
            @RequestParam String content) {

        commentService.createComment(articleId, memberId, content);
        return ResponseEntity.ok("댓글 등록 완료!");
    }
}