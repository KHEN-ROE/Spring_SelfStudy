package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 인터페이스만 만들어 놓고 JpaRepository를 구현하면 스프링 데이터 jpa가 인터페이스에 대한 구현체를 알아서 만들어냄. 그리고 스프링 빈에 등록
public interface SpringJpaDataMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    // JPQL로 번역하면 - select m from Member m where m.name = ?
    @Override
    Optional<Member> findByName(String name);
}
