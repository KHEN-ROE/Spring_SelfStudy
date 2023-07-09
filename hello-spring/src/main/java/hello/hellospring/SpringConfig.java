package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 구성정보 이용하여 스프링 빈 수동 등록
public class SpringConfig {

    @Bean // 스프링 컨테이너에 스프링 빈 수동 등록
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository(); // 구현체를 리턴
    }


}
