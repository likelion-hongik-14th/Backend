package hello.hellospring.controller;

import hello.hellospring.dto.LikeRequestDto;
import hello.hellospring.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    // 리뷰 반영: URL 노출 방지, DTO 사용
    @PostMapping
    public ResponseEntity<String> pushLike(@RequestBody LikeRequestDto requestDto) {
        likeService.pushLike(requestDto);
        return ResponseEntity.ok("좋아요 완료!");
    }
}