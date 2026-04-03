package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach //각 테스트 메서드가 끝날 때마다 실행되는 콜백 메서드
    public void afterEach() {
        //저장소를 깨끗하게 비워줌
        repository.clearStore();
    }

    @Test
    public void save() {
        //given : 주어졌을 때
        Member member = new Member();
        member.setName("spring");
        //when : 실행했을 때
        repository.save(member);
        //then : 결과가 이래야 한다
        Member result = repository.findById(member.getId()).get();
        assertThat(result).isEqualTo(member);
    }

    @Test
    public void findByName() {
        //given : 2명의 회원을 저장소에 미리 가입시킴
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);
        //when : "spring1"이라는 이름으로 회원을 찾아봄
        Member result = repository.findByName("spring1").get();
        //then : 찾아온 결과가 일치하는지 확인
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        //given : 2명의 회원을 가입시킴
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);
        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);
        //when : 저장된 모든 회원 목록을 불러옴
        List<Member> result = repository.findAll();
        //then : 저장된 회원의 총 개수가 2개인지 확인
        assertThat(result.size()).isEqualTo(2);
    }
}