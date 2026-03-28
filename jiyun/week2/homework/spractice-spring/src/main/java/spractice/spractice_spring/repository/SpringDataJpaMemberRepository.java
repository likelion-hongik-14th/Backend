package spractice.spractice_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spractice.spractice_spring.domain.Member;

import java.util.Optional;

// SpringDataJpaMemberRepository가 JpaRepository를 보고 자동으로 빈 등록해줌
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    // 스프링에서 제공하는 JpaRepository로 기본적인 기능 제공 -> 인터페이스만으로 코딩 가능
    @Override
    Optional<Member> findByname(String name);
}
