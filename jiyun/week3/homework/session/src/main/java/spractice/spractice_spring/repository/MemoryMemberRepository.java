package spractice.spractice_spring.repository;

import org.springframework.stereotype.Repository;
import spractice.spractice_spring.domain.Member;

import java.util.*;


public class MemoryMemberRepository implements MemberRepository{
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
        // ofNullable: 값이 없으면 따로 처리해줌
    }

    @Override
    public Optional<Member> findByname(String name) {
        return store.values().stream().filter(member -> member.getName().equals(name)).findAny();
        // .values() : ID는 빼고 값들만 추출
        // .stream() : 값들을 흘려보내며 데이터 흐름을 만듦
        // .filter() : 조건 필터
        // .findAny() : 발견한 데이터중 먼저 온 데이터를 반환
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    } // 모든 회원 데이터를 복사해서 반환

    public void clearStore(){
        store.clear();
    }
}
