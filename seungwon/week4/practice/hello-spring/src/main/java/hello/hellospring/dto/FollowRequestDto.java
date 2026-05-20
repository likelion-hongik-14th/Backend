package hello.hellospring.dto;

import lombok.Getter;

@Getter
public class FollowRequestDto {
    private Long followerId;
    private Long followingId;
}