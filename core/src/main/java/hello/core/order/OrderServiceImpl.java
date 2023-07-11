package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//OrderServiceImpl은 DiscountPolicy뿐만아니라 FixDiscountPolicy인 구체 클레스도 함께 의존하고 있다. DIP 위반이다.
// 또한 여기서 FixDiscountPolicy를 RateDiscountPolicy로 변경하는 순간 OrderServiceImpl의 코드를 변경해야한다.
// 따라서 OCP도 위반하고 있다
@Component
@RequiredArgsConstructor // final 이 붙은 필드에 대해서 생성자에 의존관계 주입해줌
public class OrderServiceImpl implements OrderService{

    //정리
    // AppConfig를 통해서 관심사를 확실하게 분리.
    // AppConfig는 공연 기획자이다. 구체 클래스를 선택한다. 배역에 맞는 배우를 선택한다.
    // OrderServiceImpl은 오직 기능만 담당하게 됨.

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    // 스프링이 @autowired 있는 걸 보고 new OrderServiceImpl(memberRepository, discountPolicy); 를 선언하는 것임
//    @Autowired
//    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    // 실행하는 것도 인터페이스만 보고 구현하면 됨
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
