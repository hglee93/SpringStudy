package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

/**
 * 인터페이스를 사용하는 이유?
 * 구현체를 다르게 사용할 수 있다.
 */
public class MemoryMemberRepository implements MemberRepository {

    private Map<Long, Member> store = new HashMap<>();
    private Long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values() );
    }

    public void clear() {
        store.clear();
    }
}
