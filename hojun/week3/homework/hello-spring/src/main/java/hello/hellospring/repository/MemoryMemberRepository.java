package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {
    private static Map<Long, Member> store = new HashMap<>();

    private static long sequence = 0L; //일련번호

    @Override
    public Member save(Member member) {
        //회원 가입 시 시스템이 자동으로 ID부여
        member.setId(++sequence);
        //생성된 ID와 회원 객체를 저장소에 넣는다
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        //ID로 회원을 찾는다
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Member> findAll() {
        //Map에 들어있는 모든 회원을 리스트에 담아 반환
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Member> findByName(String name) {
        //이름이 일치하는 회원을 하나라도 찾으면 반환
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }
    public void clearStore() {
        store.clear();
    }
}