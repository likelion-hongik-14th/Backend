package hello.hellospring.controller;

import hello.hellospring.dto.CommentCreateRequestDto;
import hello.hellospring.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 리뷰 반영: URL 노출 방지, DTO 사용
    @PostMapping
    public ResponseEntity<String> saveComment(@RequestBody CommentCreateRequestDto requestDto) {
        commentService.createComment(requestDto);
        return ResponseEntity.ok("댓글 등록 완료!");
    }
}