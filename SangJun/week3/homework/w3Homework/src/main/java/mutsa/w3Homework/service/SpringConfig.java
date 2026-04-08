package mutsa.w3Homework.service;

import jakarta.persistence.EntityManager;
import mutsa.w3Homework.aop.TimeTraceAop;
import mutsa.w3Homework.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    //Bean 등록 예제

    @Bean
    public MemberService memberService() {

        return new MemberService(memberRepository);
    }

    @Bean
    public TimeTraceAop timeTraceAop() {
        return new TimeTraceAop();
    }
//    @Bean
//    public MemberRepository memberRepository() {
//        return new JdbcTemplateMemberRepository(dataSource);
//        return new JpaMemberRepository(em);
//
//
//    }
}
