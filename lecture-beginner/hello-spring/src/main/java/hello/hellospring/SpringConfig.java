package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration 파일
 * 자바 클래스를 Bean 클래스로 등록하는 클래스(Configuration)
 * 해당 Config 클래스 파일을 통해, DI를 수행한다.
 * 추가적으로 MemberRepository 클래스 처럼 인터페이스를 주입하는 경우에는
 * 해당 Config 파일로 간단하게 등록함으로써, 구현체를 쉽게 바꿔줄 수 있다.
 */
@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

}
