package hello.hellospring.service;

import hello.hellospring.Entity.Follow;
import hello.hellospring.Entity.Member;
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

    public void follow(Long followerId, Long followingId) {
        // 1. 자기 자신 팔로우 방지
        if (followerId.equals(followingId)) {
            throw new IllegalArgumentException("자기 자신을 팔로우할 수 없습니다.");
        }

        // 2. 이미 팔로우 중인지 확인
        followRepository.findByFollowerAndFollowing(followerId, followingId)
                .ifPresent(f -> { throw new IllegalArgumentException("이미 팔로우 중인 회원입니다."); });

        // 3. 회원 존재 여부 확인
        Member follower = memberRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("팔로워 회원이 존재하지 않습니다."));
        Member following = memberRepository.findById(followingId)
                .orElseThrow(() -> new IllegalArgumentException("팔로잉 대상 회원이 존재하지 않습니다."));

        // 4. 팔로우 엔티티 생성 및 저장
        Follow follow = Follow.builder()
                .follower(follower)
                .following(following)
                .build();

        followRepository.save(follow);
    }
}