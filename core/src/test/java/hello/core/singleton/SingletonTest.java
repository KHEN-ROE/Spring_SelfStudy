package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        // 1. 조회: 호출할 때마다 객체 생성
        MemberService memberService1 = appConfig.memberService();

        // 1. 조회: 호출할 때마다 객체 생성
        MemberService memberService2 = appConfig.memberService();

        // 참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // memberService1 != memberService2
        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        SingletonService singletonService1 = SingletonService.getInstance(); // 객체 생성하지 않고도 메서드 접근 가능. static 메서드이기 때문
        SingletonService singletonService2 = SingletonService.getInstance(); // 객체 생성하지 않고 그냥 가져다 쓴다

        System.out.println("singletonService1 =" + singletonService1);
        System.out.println("singletonService1 =" + singletonService2);

        assertThat(singletonService1).isSameAs(singletonService2);
        //same : == 인스턴스가 같은지 비교
        //equal : equals
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {

        // 스프링컨테이너가 싱글톤 패턴 자동으로 적용함
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        // 참조값이 같은 것을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // memberService1 == memberService2
        assertThat(memberService1).isSameAs(memberService2);
    }
}
