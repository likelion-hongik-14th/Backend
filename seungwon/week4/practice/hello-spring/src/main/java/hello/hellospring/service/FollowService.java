package hello.hellospring.service;

import hello.hellospring.Entity.Follow;
import hello.hellospring.Entity.Member;
import hello.hellospring.dto.FollowRequestDto;
import hello.hellospring.repository.FollowRepository;
import hello.hellospring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    public void follow(FollowRequestDto dto) {
        if (dto.getFollowerId().equals(dto.getFollowingId())) {
            throw new IllegalArgumentException("자기 자신을 팔로우할 수 없습니다.");
        }

        // JPA 네이밍 룰에 맞게 수정
        followRepository.findByFollowerIdAndFollowingId(dto.getFollowerId(), dto.getFollowingId())
                .ifPresent(f -> { throw new IllegalArgumentException("이미 팔로우 중인 회원입니다."); });

        Member follower = memberRepository.findById(dto.getFollowerId())
                .orElseThrow(() -> new IllegalArgumentException("팔로워 회원이 존재하지 않습니다."));
        Member following = memberRepository.findById(dto.getFollowingId())
                .orElseThrow(() -> new IllegalArgumentException("팔로잉 대상 회원이 존재하지 않습니다."));

        Follow follow = Follow.builder()
                .follower(follower)
                .following(following)
                .build();

        followRepository.save(follow);
    }
}