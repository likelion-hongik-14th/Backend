package musta.homework_2nd.service;

import musta.homework_2nd.domain.Member;
import musta.homework_2nd.repository.MemberRepository;
import musta.homework_2nd.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.apache.tomcat.util.net.openssl.OpenSSLStatus.getName;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTestIntegrationTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;


    @Test
    public void 회원가입() throws Exception {
        //GIVEN
        Member member = new Member();
        member.setName("hello");

        //when
        Long saveId = memberService.join(member);

        //Then
        Member findMember = memberRepository.findById(saveId).get();
        assertEquals(member.getName(), findMember.getName());

    }


    @Test
    public void 중복_회원_예외() throws Exception {
        //given

        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

    }

}