## 회원관리 예제

1. 인터페이스의 활용

   - 해당 프로젝트에서는 데이터 저장소가 정해지지 않음(RDB, NoSQL 등)

   - 인터페이스(MemberRepository)를 활용해서 서비스 클래스를 먼저 구현함

   - 추후 구현체를 활용해서 서비스 클래스의 코드 변경을 최소화하여, 데이터저장소를 변경할 수 있음

     -> ***인터페이스의 장점***

   ```java
   public interface MemberRepository {
         Member save(Member member);
         Optional<Member> findById(Long id); // Optional에 대해서 공부 필요!!!(java8)
         Optional<Member> findByName(String name);
         List<Member> findAll();
   }
   ```

   

2. 테스트 코드 작성

   - given-when-then 순으로 작성하는 것이 좋음

     -> 어떤 데이터가 주어지고(given) 어떤 Action을 실행했을 때(when), 그때 결과는?(then)

   - JUnit 어노테이션

     - @BeforeEach : 각 테스트 코드 실행 전 실행할 코드 지정
     - @AfterEach : 각 테스트 코드 실행 후 실행할 코드 지정
     - @Test : 테스트 코드 지정



3. Component들간 의존 관계 설정

   - 의존 관계를 설정하는 방법은 두 가지가 존재한다.
     - 1) 컴포넌트 스캔과 자동 의존관계 설정
     - 2) 자바코드로 직접 스프링 빈 등록하기 -> *해당 방법은 장점이 있음*

   - 컴포넌트 스캔과 자동 의존관계 설정

     - 컴포넌트 스캔

       - 컴포넌트 스캔이란, 스프링 컨테이너가 실행될 때 특정 클래스들을 자동으로 스프링 빈 등록하는 것을 말한다.

       - 자동으로 스프링 빈 등록하는 클래스를 지정하는 방법은 @Component 어노테이션을 이용해서 지정한다.

         - MVC 프로젝트에서 자주 사용하는 @Controller, @Service, @Repository 도 자동으로 컴포넌트로 등록된다.

           -> ***그 이유는 내부에 이미 @Component 어노테이션이 지정되어있음***

     - 자동 의존관계 설정

       - 스프링 컨테이너가 클래스들간 의존 관계를 자동으로 설정해주는 것을 말한다(의존성 주입).

         - 의존 관계를 설정해주기 위해서는 스프링 컨테이너에 자바 빈 등록이 되어있어야 함.

           (컴포넌트 등록)

       - 자동 의존관계 설정을 위해서는 ***대상 클래스 생성자에 @Autowired 어노테이션을 지정한다***

   - 자바코드로 직접 스프링 빈 등록하기

     - 아래와 같은 Configuration 설정을 통해 의존 관계를 성립할 수 있다.

       ```java
       @Configuration
         public class SpringConfig {
             @Bean
             public MemberService memberService() {
                 return new MemberService(memberRepository());
             }
             @Bean
             public MemberRepository memberRepository() {
               // 구현체를 변경하기 용이하다.
               return new MemoryMemberRepository();
             }
       }
       ```

     - 해당 방식의 장점은, 구현체를 변경하여 의존성 주입을 하기 편하다는 장점이 있다.

   - 참고 사항

     - 컴포넌트 스캔 -> 주로 정형화된 컨트롤러, 서비스, 리포지토리 같은 코드
     - 자바코드를 통한 DI -> 정형화 되지 않거나, 상황에 따라 구현 클래스를 변경해야할 경우

