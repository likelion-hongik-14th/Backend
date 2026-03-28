package spractice.spractice_spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import spractice.spractice_spring.domain.Member;
import spractice.spractice_spring.service.MemberService;

import java.util.List;

@Controller
public class MemberController {
    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }
    @GetMapping("/members/new") // 보통 조회할 때
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new") // 데이터를 넣어서 보낼 것을 받을 때
                                    // ex) form 에서 post방식으로 보낼때 여기서 받음
    public String create(MemberForm form){
                        // name에 setName을 통해 데이터를 넣어주게 됨
        Member member = new Member();
        member.setName(form.getName());

        System.out.println("member = " + member.getName());

        memberService.join(member);

        return "redirect:/";
    }
    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }
}
