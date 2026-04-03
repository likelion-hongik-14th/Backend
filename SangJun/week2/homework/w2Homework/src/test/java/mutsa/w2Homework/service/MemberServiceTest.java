package mutsa.w2Homework.service;

import mutsa.w2Homework.domain.Member;
import mutsa.w2Homework.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        //테스트마다 생성자
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }
    @Test
    void join() {
        //given
        Member member = new Member();
        member.setName("John");

        //when
        Long saveId = memberService.join(member);
        //then
        Member findMember = memberService.findone(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    @Test
    void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("John");
        Member member2 = new Member();
        member2.setName("John");
        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("중복된 이름이 존재합니다");

        /*
        memberService.join(member1);
        try {
            memberService.join(member2);
            fail();
        }catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("중복된 이름이 존재합니다");
        }

         */


        //then
    }

    @Test
    void fineDone() {
    }
}