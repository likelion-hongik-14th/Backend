package spractice.spractice_spring;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spractice.spractice_spring.aop.TimeTraceAop;
import spractice.spractice_spring.repository.JpaMemberRepository;
import spractice.spractice_spring.repository.MemberRepository;
import spractice.spractice_spring.repository.MemoryMemberRepository;
import spractice.spractice_spring.service.MemberService;

@Configuration
public class SpringConfig {

    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }


}
