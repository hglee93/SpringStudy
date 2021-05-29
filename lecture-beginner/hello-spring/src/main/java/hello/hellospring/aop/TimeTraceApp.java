package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimeTraceApp {

    @Around("execution(* hello.hellospring..*(..))")
    public Object execute(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{

        long start = System.currentTimeMillis();
        System.out.println("START : " + proceedingJoinPoint.toString());

        try {
            return proceedingJoinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;

            System.out.println("END : " + proceedingJoinPoint.toString() + "(" + timeMs + "ms)");
        }
    }
}
