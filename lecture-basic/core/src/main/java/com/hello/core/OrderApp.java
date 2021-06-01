package com.hello.core;

import com.hello.core.member.MemberService;
import com.hello.core.order.OrderService;

public class OrderApp {

    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        OrderService orderService = appConfig.orderService();
    }
}
