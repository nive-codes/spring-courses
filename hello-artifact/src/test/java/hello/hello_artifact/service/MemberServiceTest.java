package hello.hello_artifact.service;

import hello.hello_artifact.domain.MemberVO;
import hello.hello_artifact.respository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


/*쉬프트 커맨트 T로 해당하는 클래스에서 단축키를 실행하면 자동으로 지금과 같은 형태로 서비스가 초기로 만들어짐*/
/*패키지도 동일하게 설정되어 있음.*/
/*컨트롤 쉬프트 R로 실행*/
/*컨트롤 R은 이전 실행 기록을 다시 실행함*/
class MemberServiceTest {

    MemberService memberService;

    MemoryMemberRepository memoryMemberRepository;
    /*memberService에 있는 MemoryMemberRespository가 다른 객체이므로 조금 애매함. static이라서 괜찮지만 만약 다르다면?*/
    /*memberService에 있는 memberRepository를 받아서 처리하도록 수정*/

    @BeforeEach
    void BeforeEach(){
        memoryMemberRepository = new MemoryMemberRepository();      /*만든 후 */

        memberService = new MemberService(memoryMemberRepository);  /*service에 만들어진 respository를 넘겨줌*/
        /*memberService입장에서 new MemberResository를 만들어주는게 아닌 넣어주니까, Dependency Injection(DI) */
    }

    @AfterEach
    void afterEach() {
        memoryMemberRepository.clearStore();
    }

    /*테스트 코드는 한글로 작성해도 되는데 케바케*/
    @Test
    void join() {
        /*given when then 기법(기본 패턴)(이 데이터를 이렇게 검증해서 이렇게 처리 해라 라고 주석을 처리)*/

        //given
        MemberVO memberVO = new MemberVO();
        memberVO.setName("userName");

        //when
        Long uniqId = memberService.join(memberVO);

        //then
        MemberVO resultVO = memberService.findMemberById(uniqId).get();
        /*static import로 import하고 싶을때 option + enter*/
        assertThat(memberVO.getName()).isEqualTo(resultVO.getName());
    }

    /*테스트에서는 예외도 중요하므로 테스트 필요*/
    @Test
    void dupTest(){
        //given
        MemberVO memberVO = new MemberVO();
        memberVO.setName("userName");

        MemberVO memberVO2 = new MemberVO();
        memberVO2.setName("userName");

        //when
        memberService.join(memberVO);
        /*memberservice.join(memberVO2)를 넣었을때 IllegalStateException이 터져야된다라는 람다*/
        assertThrows(IllegalStateException.class , () -> memberService.join(memberVO2));

        /*option + command + 엔터로 객체 생성*/
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class, () -> memberService.join(memberVO2));
        assertThat(illegalStateException.getMessage()).contains("이미 존재하는 회원입니다.");


        /*테스트에서 try catch는 좀 애매함*/
        /*try{
            memberService.join(memberVO2);
            fail("duplicated member");
        }catch (IllegalStateException e){
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.123123");
        }*/


        //then


    }

    @Test
    void findMembers() {
    }

    @Test
    void findMemberById() {
    }
}