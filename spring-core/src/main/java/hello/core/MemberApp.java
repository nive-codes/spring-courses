package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
//        MemberService memberService = new MemberServiceImpl();

//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();

        /*스프링 컨에티너 생성*/
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        /*AppConfig의 환경정보를 가지고 생성-bean을 스프링 컨테이너에 넣어서 관리*/

        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);/*기본적으로 메서드 이름으로 등록됨, 두번째는 반황 타입*/


        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("member : " + member.getName());
        System.out.println("find Member : " + findMember.getName());

        /*순수한 자마 코드에 메서드 실행 및 테스트*/
    }
}
