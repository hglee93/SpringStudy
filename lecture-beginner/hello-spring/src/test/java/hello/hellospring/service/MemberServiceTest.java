package hello.hellospring.service;

import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemoryMemberRepository memberRepository;
    MemberService memberService;

    @BeforeEach // 각 테스트 실행 전에 실행되는 테스트 코드. 일반적으로 객체 Initialize를 수행하는 것으로 보임
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach // 각 테스트 케이스 실행이 끝날 때마다 실행되는 테스트 코드.
    public void afterEach() {
        memberRepository.clear();
    }

    @Test
    void join() {
        // 테스트 케이스는 항상 given, when, then을 생각하자
        // given : 어떤 데이터가 주어지고(given)
        // when : 어떤 Action을 실행했을 때(when)
        // then : 그때 결과가 맞는가?(then)
        // given
        Member member = new Member();
        member.setName("spring");

        // when
        Long saveId = memberService.join(member);

        // then
        Member result = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(result.getName());
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);

        // try-catch로 Exception을 처리해도 되지만~,
        // assertThrows로도 예상한 Exception이 발생하는지 확인할 수 있다.
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        /* 위와 비교하기
        try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
        */

        // then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}