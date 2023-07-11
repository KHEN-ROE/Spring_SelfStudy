package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component // 이렇게 해도 되고 설정 클래스에 수동으로 빈 등록해도 됨
public class TimeTraceAop {

    @Around("execution(* hello.hellospring..*(..))") // 전역에 적용
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("start: " + joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("end: " + joinPoint.toString() +" " + timeMs + "ms");
        }
    }
}
