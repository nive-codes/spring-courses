package hello.hello_artifact.respository;

import hello.hello_artifact.domain.MemberVO;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

//static 변수로 import하게 되면 assertThat과 같은 비교를 짧은 코딩으로 사용 가능
import static org.assertj.core.api.Assertions.*;

/*퍼블릭은 굳이 안해도 됨. 테스트이기 때문에*/
/*클래스에서 동작을 하는 경우 전체 동작됨*/
/*메서드별로 따로 동작하게 되므로 순서때문에 오류가 발생할 수 있음.*/
/*테스트는 의존관계 없이 작성해야함.*/
/*테스트를 먼저 작성 후 클래스들을 작성해도 됨. 테스트 주도개발 TDD라고 표현*/
/*test.java.hello.hello_artifact 우클릭 후 실행으로 모든 테스트 코드 실행 가능*/
/*혹은 ./gradlew test로 처리*/
public class MemoryMemberRepositoryTest {

//    MemberRepository respository  = new MemoryMemberRepository();
/*멤버레포지토리가 아닌 MemoryMemberRepository만 테스트하므로 객체를 따로 생성 후 테스트하고, afterEach로 초기화*/

    MemoryMemberRepository respository  = new MemoryMemberRepository();

    /*콜백 메서드가 실행될 때마다 실행되는 메서드를 선언하는 어노테이션*/
    /*테스트 끝날 때 마다 초기화*/
    @AfterEach
    public void afterEach(){
        respository.clearStore();
    }

    @Test
    public void save(){
        MemberVO member = new MemberVO();
        member.setName("spring");


        respository.save(member);

        MemberVO result = respository.findById(member.getId()).get();
        /*같은지비교*/
        /*member 가 저장된 result와 같은지*/
        assertThat(member).isEqualTo(result);
    }


    @Test
    public  void findById(){
        MemberVO member1 = new MemberVO();
        member1.setName("spring1");
        respository.save(member1);


        MemberVO member2 = new MemberVO();
        member2.setName("spring2");
        respository.save(member2);

        MemberVO result = respository.findByUsername("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        MemberVO memberVO = new MemberVO();
        memberVO.setName("spring1");
        respository.save(memberVO);

        MemberVO memberVO2 = new MemberVO();
        memberVO2.setName("spring2");
        respository.save(memberVO2);

        assertThat(respository.findAll()).hasSize(2);
    }
}
