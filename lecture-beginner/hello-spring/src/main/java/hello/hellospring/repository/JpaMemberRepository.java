package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    /***
     * 의존성으로 data.jpa를 추가하게 되면
     * 스프링 컨테이너는 EntityManager 라는 스프링 빈을 알아서 만들어 둠
     * JPA를 쓰고자 하는 클래스는 EntityManager를 주입 요청하면 스프링이 DI를 해준다.
     */
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        /***
         * select m from Member m
         * -> JPQL : 객체를 대상으로 쿼리를 함. SQL로 번역됨.
         * -> 현재는 이렇게도 쓰인다 정도만 알고 추후 더 깊게 공부할 필요가 있음.
         */
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
