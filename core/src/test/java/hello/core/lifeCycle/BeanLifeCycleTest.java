package hello.core.lifeCycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        // 스프링 컨테이너에 등록
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        // 스프링 빈 조회
        NetworkClient client = ac.getBean(NetworkClient.class);
        ac.close();
    }

    @Configuration
    public static class LifeCycleConfig {

        // 스프링 빈이 생성되고 호출된 결과물이 스프링 빈으로 등록이 된다.
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient(); // 생성자 호출
            networkClient.setUrl("http://hello-spring.dev"); // 객체 생성 완료 후에 세터 설정
            return networkClient;
        }
    }
}
