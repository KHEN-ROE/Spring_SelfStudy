package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryOld {

//    @PersistenceContext // jpa가 제공하는 표준 어노테이션. 스프링부트를 사용하면 생성자 주입 방식으로 바꿀 수 있다.
    private final EntityManager em; // 스프링이 EntityManager 만들어서 주입해줌

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id); // type, pk
    }

    // 여기부턴 JPQL 써야함. 엔티티 객체를 대상으로 쿼리를 한다
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where name = :name", Member.class).setParameter("name", name).getResultList(); // :name 은 파라미터 바인딩
    }

}
