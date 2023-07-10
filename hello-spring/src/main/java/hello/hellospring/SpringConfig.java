package hello.hellospring;

import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration // 구성정보 이용하여 스프링 빈 수동 등록
public class SpringConfig {

    private final DataSource dataSource;

    private final EntityManager em;

    @Autowired
    public SpringConfig(DataSource dataSource, EntityManager em) {
        this.dataSource = dataSource;
        this.em = em;
    }

    @Bean // 스프링 컨테이너에 스프링 빈 수동 등록
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository(); // 구현체를 리턴
//        return new JdbcMemberRepository(dataSource); // MemberRepository 인터페이스를 구현한 구현체만 바꿔끼운다. 이게 객체지향의 다형성.
//        return new JdbcTemplateMemberReposiotry(dataSource);
        return new JpaMemberRepository(em);
    }


}
