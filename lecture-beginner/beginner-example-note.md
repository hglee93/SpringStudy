## 회원관리 예제

1. 인터페이스의 활용

   - 해당 프로젝트에서는 데이터 저장소가 정해지지 않았다라고 가정합니다.(RDB, NoSQL 등)

   - 위의 가정으로 인해 인터페이스(MemberRepository)를 생성함으로써 데이터 저장소가 정해지지 않아도 서비스 클래스를 함께 구현할 수 있습니다. -> ***인터페이스의 장점 1. 병렬로 개발할 수 있다.***

   - 또한, 인터페이스를 활용한다면 인터페이스의 구현체만 변경함으로써 서비스 클래스의 코드 변경을 최소화하면서 데이터저장소를 변경할 수 있습니다.

     -> ***인터페이스의 장점 2. 의존관계 클래스의 변경을 최소화할 수 있다.(개방-폐쇄 원칙)***

   ```java
   public interface MemberRepository {
         Member save(Member member);
         Optional<Member> findById(Long id); // Optional에 대해서 공부 필요!!!(java 8)
         Optional<Member> findByName(String name);
         List<Member> findAll();
   }
   ```

   ***개방-폐쇄 원칙이란***

   -   모듈의 확장에는 열려있고, 수정에는 닫혀있다 라는 원칙
   -   확장이란? 어플리케이션의 요구사항이 변경되었을 때, 이 변경 사항을 반영할 때 새로운 동작을 추가함으로써 모듈을 확장할 수 있다는 개념이다.
       -   해당 프로젝트에서처럼 메모리 DB에서 Jdbc를 사용해야할 때, JdbcTemplateRepository 구현체를 추가함으로써 모듈을 확장시킬 수 있다.
   -   폐쇄란? 모듈의 소스 코드 변경 없이 모듈의 기능을 확장하거나 변경할 수 있다라는 개념이다. 즉 기존의 코드를 건드릴 필요 없이 변경할 수 있어야한다는 개념이다.
       -   해당 프로젝트에서는 Memory DB -> RDB로 변경할 때, 이를 의존하고 있는 서비스 클래스의 변경 없이 구현체를 변경함으로써 모듈의 기능을 변경할 수 있었다.
   -   주로 OOP에서는 추상화를 통해서 이 원칙을 구현할 수 있다고 한다.

2. 테스트 코드 작성

   - given-when-then 순으로 작성하는 것이 좋다고 합니다.

     -> 어떤 데이터가 주어지고(given) 어떤 Action을 실행했을 때(when), 그때 결과는?(then) 순으로 작성하도록 노력하자.

   - JUnit 어노테이션 정리

     - @BeforeEach : 각 테스트 코드 실행 전 실행할 코드 지정
     - @AfterEach : 각 테스트 코드 실행 후 실행할 코드 지정
     - @Test : 테스트 코드 지정
     - @Transactional : 테스트 코드에 해당 어노테이션을 지정하면, 테스트 시작 전에 트랜잭션을 실행하고 테스트가 끝난 후에 롤백한다(테스트 코드 뿐만 아니라 실제 DB CRUD 코드에도 사용된다)

3. Component들간 의존 관계 설정

   - Spring에서 의존 관계를 설정하는 방법은 두 가지가 존재합니다.
     - 1) 컴포넌트 스캔과 자동 의존관계 설정
     - 2) 자바코드로 직접 스프링 빈 등록하기 -> *해당 방법은 장점이 있음*

   

   - 1) 컴포넌트 스캔과 자동 의존관계 설정

       - 컴포넌트 스캔

         - 컴포넌트 스캔이란, 스프링 컨테이너가 실행될 때 특정 클래스들을 자동으로 스프링 빈으로 등록하는 것을 말합니다.

         - 자동으로 스프링 빈 등록하는 클래스를 지정하는 방법은 @Component 어노테이션을 이용해서 지정한다.

           - MVC 프로젝트에서 자주 사용하는 @Controller, @Service, @Repository 도 자동으로 컴포넌트로 등록된다.

             -> ***그 이유는 내부에 이미 @Component 어노테이션이 지정되어있기 때문이다.***

       - 자동 의존관계 설정

         - 스프링 컨테이너가 클래스들간 의존 관계를 자동으로 설정해주는 것을 말합니다. (의존성 주입)

           - 스프링 컨테이너가 자동으로 의존 관계를 설정해주기 위해서는 스프링 빈이 등록 되어있어야 합니다.

             (컴포넌트 등록)

         - 자동 의존관계 설정을 위해서는 ***대상 클래스 생성자에 @Autowired 어노테이션을 지정함***으로써 의존성을 주입합니다.

   
   
   
   - 2) 자바코드로 직접 스프링 빈 등록하기
   
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
   
     - 해당 방식의 장점은, 의존성을 주입할 때 구현체를 변경하기 쉽다는 장점을 가지고 있습니다.
   
         - 1)의 방식으로 사용할 경우, 구현체를 변경하는 방법은??
             - 1) @Autowired 어노테이션이 지정된 생성자로 가서
             - 2) 생성자 안에 있는 구현체를 변경해준다.
   
   
   
   
   - ***참고 사항***
     - 컴포넌트 스캔 -> 주로 정형화된 컨트롤러, 서비스, 리포지토리 같은 코드
     - 자바코드를 통한 DI -> 정형화 되지 않거나, 상황에 따라 구현 클래스를 변경해야할 경우





## 스프링 DB 접근 기술

스프링에서 DB에 접근하는 기술은 다음과 같습니다.

- 순수 JDBC
- 스프링 JdbcTemplate
- JPA
- 스프링 데이터 JPA

아래로 내려가면서 발전된 기술이라고 할 수 있는데, 각 기술의 차이점은 ***코드의 중복도를 줄여주고 생산성을 높이는 방향***으로 발전되었다고 볼 수 있습니다. 

1.  순수 JDBC

    순수 JDBC를 사용했을 경우 아래와 같은 코드를 통해 DB를 연결하여 데이터를 조회 합니다. 

    ***-> 쿼리를 작성해야하고, 코드가 길며 매번 같은 코드 반복***

    ```java
    public List<Member> findAll() {
    		String sql = "select * from member";
      	
      	Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
        		conn = getConnection();
        		pstmt = conn.prepareStatement(sql);
        		rs = pstmt.executeQuery();
          
        		List<Member> members = new ArrayList<>(); 
          	while(rs.next()) {
        				Member member = new Member(); 
              	member.setId(rs.getLong("id")); 
              	member.setName(rs.getString("name")); 
              	members.add(member);
            }
          
            return members;
         } catch (Exception e) {
            throw new IllegalStateException(e);
         } finally {
            close(conn, pstmt, rs);
         }
    }
    ```

    또한, DB를 사용하기 전 Configuration 파일에 DataSource를 아래와 같이 등록해야 합니다. DataSource란 데이터베이스 커넥션을 획득할 때 사용하는 객체입니다. 스프링 부트는 데이터베이스 커넥션 정보를 바탕으로 미리 DataSource를 생성하고 스프링 빈으로 만들어 둔다. 그래서 DI를 해줄 수 있게 됩니다.

    ```java
    @Configuration
    public class SpringConfig {
        private final DataSource dataSource;
        public SpringConfig(DataSource dataSource) { 
          this.dataSource = dataSource;
        }
    }
    ```

2.  스프링 JdbcTemplate

    스프링 JdbcTemplate를 사용했을 경우 아래와 같은 코드를 통해 DB를 연결하여 데이터를 조회 합니다. 

    ***-> 순수 JDBC API의 코드를 많이 줄여주지만, 아직까지 SQL을 작성해야한다.***

    ```java
    @Override
    public Optional<Member> findById(Long id) {
      	List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
      	return result.stream().findAny(); 
    }
    
    @Override
    public List<Member> findAll() {
      	return jdbcTemplate.query("select * from member", memberRowMapper()); 
    }
    
    private RowMapper<Member> memberRowMapper() { 
      	return (rs, rowNum) -> {
          	Member member = new Member(); 
          	member.setId(rs.getLong("id")); 
          	member.setName(rs.getString("name")); 
          	return member;
        }; 
    }
    ```

    

3.  
