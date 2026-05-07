package hello.hellospring.controller;

import hello.hellospring.dto.FollowRequestDto;
import hello.hellospring.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/follows")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    // 리뷰 반영: URL 노출 방지, DTO 사용
    @PostMapping
    public ResponseEntity<String> follow(@RequestBody FollowRequestDto requestDto) {
        followService.follow(requestDto);
        return ResponseEntity.ok("팔로우 성공!");
    }
}