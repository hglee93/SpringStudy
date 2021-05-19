### Spring 라이브러리 정리

스프링으로 웹 프로젝트를 만들 때, 기본적으로 추가해야되는 라이브러리는 아래와 같다.

- spring-boot-starter-web : WEB 프로젝트를 만들 때, 필요한 라이브러리
  - spring-boot-starter-tomcat : 톰캣(웹서버) --> 내장 웹 서버도 포함이겠지?
  - spring-webmvc : 스프링 웹 MVC
- spring-boot-starter-thymeleaf : 타임리프 템플릿 엔진(View) --> viewResolver 사용
- spring-boot-starter : 스프링을 실행시키는 데, 필요한 공통적인 모듈 라이브러리이다.
  - spring-boot : 스프링 코어!
    - spring-core
  - spring-boot-starter-logging : 로깅에 필요한 라이브러리
    - logback, slf4j



### View 환경설정

스프링의 경우, 정적리소스는 resources 폴더에 저장하며 스프링 자체에서도 해당 폴더에서 가장 먼저 찾는다.

- 가장 기본적인 Wecome Page 기능은 static/index.html 이다.

만약 템플릿 엔진을 사용할 경우, URL 입력 시 아래의 실행 흐름을 갖는다.



1) 스프링 컨테이너가 URL을 받게 되면, 해당 Path의 Controller를 찾게 되고 만약 존재한다면 그 Controller를 실행한다.

2) 컨트롤러에서 문자를 반환하면 ***뷰 리졸버***가 화면을 찾아서 처리한다. (뷰 리졸버는 템플릿 엔진을 지정했을 경우 실행됨)



### 스프링 웹 개발 기초

스프링에서 컨텐츠를 제공하는 방법으로 3가지가 존재

1. 정적 페이지 : 그냥 HTML 리턴
2. MVC와 템플릿 엔진 : 서버 렌더링을 거쳐 HTML로 변환 후 클라이언트에게 리턴
3. API : 형식을 자유롭게 HTTP Body에 데이터를 반환하여 리턴

#### 정적페이지 실행 흐름

스크린샷 2021-05-19 오전 10.49.51![image](https://user-images.githubusercontent.com/15210906/118744848-05615b80-b890-11eb-8e0f-5a2da1a6b663.png)

1. 가장 먼저 hello-static 과 관련된 컨트롤러가 있는지 확인
2. 없기 때문에, resources/static 폴더의 정적 파일을 찾아서 반환


#### MVC, 템플릿 엔진 실행 흐름

![image](https://user-images.githubusercontent.com/15210906/118744927-2a55ce80-b890-11eb-8f25-deb5b5c8964e.png)

1. Request가 왔을 경우, 관련된 컨트롤러가 있는지 확인(컨트롤러 클래스 이름으로 확인)
2. 있으면, 처리 후 템플릿 이름, model을 viewResolver에게 전달
3. viewResolver는 templates 폴더 밑에 있는 템플릿 파일을 렌더링하여 HTML로 변환 후 클라이언트에게 반환

   --> 템플릿 파일을 렌더링하는 것은 뷰리졸버? 템플릿 엔진? : 템플릿 엔진!

   -> 뷰 리졸버는 템플릿을 선택하는 역할만 할듯한데,,,,



### API

- API 방식의 경우, @ResponseBody를 사용하여 메서드에 붙여줘야함
- @ResponseBody를 붙이면 뷰 리졸버를 사용하지 않음
- @ResponseBody를 붙이고 객체를 반환하면 자동으로 JSON 변환
- @ResponseBody를 붙이면 관련 컨트롤러에서 처리 후 ***HttpMessageConverter***가 동작

![image](https://user-images.githubusercontent.com/15210906/118744979-49ecf700-b890-11eb-9a7d-9543ea66f278.png)
