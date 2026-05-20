package hello.hellospring.controller;

import hello.hellospring.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/follows")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    // 팔로우 하기: POST http://localhost:8080/api/v1/follows?followerId=1&followingId=2
    @PostMapping
    public ResponseEntity<String> follow(
            @RequestParam Long followerId,
            @RequestParam Long followingId) {

        followService.follow(followerId, followingId);
        return ResponseEntity.ok("팔로우 성공!");
    }
}