package com.hello.core.singleton;

import com.hello.core.AppConfig;
import com.hello.core.member.MemberRepository;
import com.hello.core.member.MemberService;
import com.hello.core.member.MemberServiceImpl;
import com.hello.core.order.Order;
import com.hello.core.order.OrderService;
import com.hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {

    @Test
    @DisplayName("Bean이 실제로 하나만 생성되는지 확인")
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        //System.out.println("memberService -> memberRepository = " + memberService.getMemberRepository());
        //System.out.println("orderService -> memberRepository = " + orderService.getMemberRepository());
        //System.out.println("memberRepository = " + orderService.getMemberRepository());

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
    }
}
