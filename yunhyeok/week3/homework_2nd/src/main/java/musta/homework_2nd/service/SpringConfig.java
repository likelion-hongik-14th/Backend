package musta.homework_2nd.service;


import jakarta.persistence.EntityManager;
import musta.homework_2nd.repository.*;
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


    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository); //()안에 무언가를 넣어줘야함

    }

  //  @Bean
 //   public MemberRepository memberRepository() {
 //       return new MemoryMemberRepository();
 //       return new JdbcMemberRepository(dataSource);
 //       return new JdbcTemplateMemberRepository(dataSource);
   //     return new JpaMemberRepository(em);
 //   }
}
