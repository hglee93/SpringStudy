package com.hello.core;

import com.hello.core.member.Grade;
import com.hello.core.member.Member;
import com.hello.core.member.MemberService;
import com.hello.core.order.Order;
import com.hello.core.order.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {

    public static void main(String[] args) {

        // Generate Spring Container
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        // get memberService bean and orderService bean
        // * Pass AppConfig and Generate applicationContext
        // * So Spring Container register bean, that written on AppConfig Class, to Spring Bean Store
        // * In Configuration Class, the method name becomes bean name. Return type becomes bean type.
        // * 이렇게 하기 위해서는, AppConfig 클래스에 @Configuration 어노테이션 생성, 빈을 스프링 컨테이너에 등록하기 위해서는 @Bean을 명시해주어야 한다.
        // * Bean name must be unique. otherwise side effect happens.
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);

        Member member = new Member(1L, "member", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(member.getId(), "itemA", 20000);

        System.out.println("order : " + order.toString());

    }
}
