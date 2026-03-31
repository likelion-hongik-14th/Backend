package musta.homework_2nd.service;


import musta.homework_2nd.repository.MemberRepository;
import musta.homework_2nd.repository.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository()); //()안에 무언가를 넣어줘야함

    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
