package hello.hellospring.repository;
<<<<<<< HEAD
import hello.hellospring.domain.Member;
import java.util.List;
import java.util.Optional;

=======

import hello.hellospring.domain.Member;
import java.util.List;
import java.util.Optional;
>>>>>>> 0f9a2779c2d81ba69f2b0ee63764d0ea67e5c9b9
public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
