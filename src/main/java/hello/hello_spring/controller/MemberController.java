package hello.hello_spring.controller;

import hello.hello_spring.domain.Member;
import hello.hello_spring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @Controller가 있으면 스프링이 제공하는 컨트롤러여서 스프링 빈으로 자동 등록됨.
 * 스프링 빈을 등록하는 2가지 방법
 * 1. 컴포넌트 스캔과 자동 의존관계 설정
 * 2. 자바 코드로 직접 스프링 빈 등록
 */
@Controller
public class MemberController {
    private final MemberService memberService;

    /**
     * DI(의존성 주입)에는 필드, setter, 생성자 주입으로 3가지 방법 존재
     * 의존관계가 실행 중에 동적으로 변하는 경우는 겅의 없으므로
     * 아래와 같이 "생성자 주입"을 권장
     */
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String createMember(MemberForm memberForm) {
        Member member = new Member();
        member.setName(memberForm.getName());

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
