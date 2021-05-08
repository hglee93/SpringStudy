package hello.hellospring.repository;

import hello.hellospring.Repository.MemberRepository;
import hello.hellospring.Repository.MemoryMemberRepository;
import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach // 각 테스트 케이스가 끝날때 마다 실행되는 메서드
    public void afterEach() {
        repository.clear();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");
        repository.save(member);
        Member result = repository.findById(member.getId()).get();

        Assertions.assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("Spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("Spring2");
        repository.save(member2);

        Member result = repository.findByName("Spring1").get();

        Assertions.assertThat(member1).isEqualTo(result);
    }

    @Test
    public void findAll() {

        Member member1 = new Member();
        member1.setName("Spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("Spring2");
        repository.save(member2);

        List<Member> list = repository.findAll();

        Assertions.assertThat(list.size()).isEqualTo(2);

    }

}
