package hello.hellospring.service;
import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
=======
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
>>>>>>> 0f9a2779c2d81ba69f2b0ee63764d0ea67e5c9b9

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    //회원을 저장하고 찾기 위해 저장소 객체를 생성
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long join(Member member) {
<<<<<<< HEAD
        long start = System.currentTimeMillis();
        try {
            validateDuplicateMember(member); //중복 회원 검증
            memberRepository.save(member);
            return member.getId();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("join " + timeMs + "ms");
        }
=======
        // 1. 같은 이름이 있는 중복 회원 검
        validateDuplicateMember(member);
        // 2. 검증이 통과되면 저장소에 저장
        memberRepository.save(member);
        return member.getId();
>>>>>>> 0f9a2779c2d81ba69f2b0ee63764d0ea67e5c9b9
    }
    //중복 회원 검증 메서드
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    //값이 이미 존재한다면 에러 발생
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
    // 전체 회원 조회
    public List<Member> findMembers() {
<<<<<<< HEAD
        long start = System.currentTimeMillis();
        try {
            return memberRepository.findAll();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("findMembers " + timeMs + "ms");
        }    }
=======
        return memberRepository.findAll();
    }
>>>>>>> 0f9a2779c2d81ba69f2b0ee63764d0ea67e5c9b9
    // 특정 회원 한 명 조회
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}