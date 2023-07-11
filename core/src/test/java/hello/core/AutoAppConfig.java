package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
// @Component 어노테이션이 붙은 클래스를 자동으로 스프링 빈으로 등록
@ComponentScan(
        basePackages = "hello.core.member", // 탐색할 패키지 지정
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {


}
