package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository; // 여기서 new MemoryMemberRepository를 선언하지 않는다. 즉 의존하지 않게 한다.
                                                        // 대신, 생성자를 하나 만들고 이를 통해 접근하도록 한다

    @Autowired // MemberRepository 타입에 맞는 스프링 빈을 주입해준다.
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository; 
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
