package hello.hello_artifact.controller;

import hello.hello_artifact.domain.MemberVO;
import hello.hello_artifact.service.MemberService;
import hello.hello_artifact.vo.MemberForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Member;
import java.util.List;

/*스프링이 실행 될 때 controller를 가져와서 -> service -> repository */
/*컨트롤러가 생성될 때 스프링 빈에 등록되어 있는 개체를 넣어줌 -> DI(의존관계 주입을 해줌) - 싱글톤(딱 한개만 생성해서 공유함-메모리 절약하고 좋음)*/
/*@service(@Component가 안에 있음) @repository의 방식이 컴포넌트(각 애노테이션 안에 있기 때문에 스트링 컨테이너에 빈 객체가 등록됨) 스캔 방식 */
/*컴포넌트 스캔을 하려고 하면 기본적으로 hello.hello_artifact 하위에서만 스프링이 조회해서 객체를 등록하므로, 타 패키지는 안됨.*/
@Controller
public class MemberController {

//    private final MemberService memberService = new MemberService();
//    이거보다는 다른 컨트롤러들이 가져가서 쓸 수 있도록 스프링컨테이너에 하나만 등록해서 생성해서 사용하도록 해야된다. 하나만 생성해서 공용으로 쓰도록


// 이렇게 했을 때 spring이 실행될 때 service가 뭔지 알 수가 없으므로
//    private final MemberService memberService;
//
//
//    @Autowired
//    public MemberController(MemberService memberService) {
//        this.memberService = memberService;
//    }
// memberService에 @Service 어노테이션을 달아두면 작동이 됩니다.

private final MemberService memberService;


    @Autowired
    public MemberController(MemberService memberService) {
    this.memberService = memberService;
    }


    /*GetMapping 및 PostMapping이냐에 따라서 매핑을 같이 쓸 수도 있음.*/
    /*RequestMapping인 경우 에러가 보통 나왔었는데, 이 경우 모든 http메소드에 해당해서 적용이라서.*/

    @GetMapping(value = "/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String create(MemberForm form) {
        MemberVO member = new MemberVO();

        member.setName(form.getName());

        memberService.join(member);
        return "redirect:/";
    }


    @GetMapping(value = "/members")
    public String list(Model model) {
        List<MemberVO> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }

}
