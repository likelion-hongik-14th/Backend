package hello.hellospring.repository;
import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

<<<<<<< HEAD
public interface SpringDataJpaMemberRepository extends JpaRepository<Member,
        Long>, MemberRepository {
=======
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
>>>>>>> 0f9a2779c2d81ba69f2b0ee63764d0ea67e5c9b9
    Optional<Member> findByName(String name);
}