package hello.hello_artifact.service;

import hello.hello_artifact.domain.MemberVO;
import hello.hello_artifact.respository.MemberRepository;
import hello.hello_artifact.respository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest /*Spring 컨테이너와 같이 테스트를 진행할 때*/
@Transactional  //트랜잭션(테스트로 넣었는데 굳이 남길 필요가 없으니 rollback해주는게 좋다)
class MemberServiceIntigrationTest {

    /*테스트는 제일 끝단이므로 생서자를 통한 inection이 좋지만 굳이 안해도 됨. 다른데 가져가서 쓸게 아니라 여기서 테스트하고 종료임*/
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;


    @Test
    void join() {

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

 /*   @Test
    void findMembers() {
    }

    @Test
    void findMemberById() {
    }*/
}