package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.dialect.IDialect;

import java.util.List;

@Service // component 어노테이션이 붙어있기 때문에 컴포넌트 스캔의 대상이 됨
@Transactional(readOnly = true) // jpa의 데이터 변경이나 로직들은 가급적 transactional 안에서 다 실행되어야 한다.
                                // 조회 메서드에서 readOnly = true 하면 성능최적화 됨.
@RequiredArgsConstructor // final 이 있는 필드에 대해 생성자 만들어줌
public class MemberService {

    private final MemberRepository memberRepository;

    //회원 가입
    @Transactional
    public Long join(Member member) {
        // 중복 회원 검사
        validateDuplicateMember(member);
        // db에 저장
        memberRepository.save(member);
        return member.getId(); // 뭐가 저장됐는지 알기 위해 id리턴
    }

    private void validateDuplicateMember(Member member) {
        //EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 한 명 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id); // 변경감지 기능 사용. 영속 상태의 member의 이름을 바꿔준다.
        // 그러면 스프링 aop가 동작한다. @Transactional 어노테이션에 의해서
        // 트랜잭션 aop가 끝나는 시점에 커밋이 됨. 그때 jpa가 flush, commit을 함.
        member.setName(name);
    }
}
