package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional // 데이터를 저장, 변경할 때는 서비스 계층에 항상 @Transactional 붙여야함
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired //@requiredArgs 로 바꿀 수 있음(lombok 추가하고)
    public MemberService(MemberRepository memberRepository) { // 외부에서 넣어준다. 이런 것을 의존관계 주입이라고 함
        this.memberRepository = memberRepository;
    }

    // 회원가입
    public Long join(Member member) {

        // 같은 이름이 있는 중복회원은 안 된다.
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원");
                });
    }

    // 전체회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }


}
