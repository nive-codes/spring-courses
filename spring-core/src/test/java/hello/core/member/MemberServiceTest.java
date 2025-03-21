package hello.core.member;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class MemberServiceTest {

//    MemberService memberService = new MemberServiceImpl();
    MemberService memberService;

    /*사용할떄마다 돌도록 처리*/
    /*같은 변수 참조할때 오류 생길 수도 있으므로*/
    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    void join(){
        //given
        Member member = new Member(1L, "memberA", Grade.VIP);

        //when
        memberService.join(member);
        Member findMember = memberService.findMember(1L);
//        Member findMember = memberService.findMember(2L);

        //then
        Assertions.assertThat(member).isEqualTo(findMember);
    }
    /*요즘에는 테스트 개발을 필수라고 보면 된다.*/

}