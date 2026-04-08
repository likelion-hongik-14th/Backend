package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    /*회원가입*/
    public Long join(Member member){
        //같은 이름이 있는 중복 회원x
        //optional단축기 ctrl + alt + v
//        Optional<Member> result = memberRepository.findByName(member.getName());
//        result.ifPresent(m->{
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        }); 이렇게 할 수 도 있는데 optional을 바로 반환하는것은 좋지 않음

        validateDuplicatemember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicatemember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("Member already exists");
                        });
    }

    /*전체회원 조회*/
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
