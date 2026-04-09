package mutsa.hello_spring;

import mutsa.hello_spring.repository.MemberRepository;
import mutsa.hello_spring.repository.MemoryMemberRepository;
import mutsa.hello_spring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
