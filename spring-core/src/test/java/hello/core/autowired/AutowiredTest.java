package hello.core.autowired;

import hello.core.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void autowiredOption(){
        // 임의 스프링 생성
        /*spring bean 등록이 됨 testbean도. 이렇게 컨테이너 생성할 때 자동으로 만들어짐(물론 CGILIB 인가 그걸로 되지만 여튼 등록)*/
        /*스프링 컨테이너 올라올때 autowired 다 실행되기 때문에 밑의 TestBean 내용을 확인할 수 있다.*/
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);



    }

    static class TestBean{
        //호출 안됨
        @Autowired(required = false) //의존 관계가 없으면 의존관계 호출이 아예 되지 않음.
        public void setNoBean1(Member member) { //member는 스프링 컨테이너 관리가 안되고 있으므로 테스트하기 좋음.
            System.out.println("setNoBean1 = " + member);
        }
        //null 호출
        @Autowired
        public void setNoBean2(@Nullable Member member) {
            System.out.println("setNoBean2 = " + member);
        }
        //Optional.empty 호출
        @Autowired(required = false)
        public void setNoBean3(Optional<Member> member) {
            System.out.println("setNoBean3 = " + member);
        }
    }



}
