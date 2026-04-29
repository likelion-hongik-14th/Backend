package musta.homework_2nd.repository;

import musta.homework_2nd.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository  extends JpaRepository<Member,Long>, MemberRepository {

    @Override
    Optional<Member> findByName(String name);
}
