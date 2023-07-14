package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new") // 회원가입 버튼을 누르면 하이퍼링크를 통해 /members/new URL로 이동하게 되고
    // 동시에 이 엔드포인트로 GET요청하게 됨(리액트 처럼 axios가 없으니).
    // 즉 @GetMapping("/members/new") 이것은 url을 의미한다.
    // 엔드포인트와 URL은 일치한다!
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "/members/createMemberForm"; // 위의 경로로 들어오면 이 템플릿을 보여준다
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm memberForm, BindingResult result) { // @vailid 쓰면 MemberForm 에 있는 어노테이션을 벨리데이션 한다.
        // 따라서 @NotEmpty가 적용돼서 회원 이름 필수로 넣게 한다.
        // BindingResult에 의해 @valid에 의한 오류가 있어도 아래 코드 실행

        if (result.hasErrors()) {
            return "/members/createMemberForm";
        }

        Address address = new Address(memberForm.getCity(), memberForm.getStreet(), memberForm.getZipcode());

        Member member = new Member();
        member.setName(memberForm.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
