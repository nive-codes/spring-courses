package hello.core.beanFind;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextBasicFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName(){
        MemberService service = ac.getBean("memberService",MemberService.class);
        assertThat(service).isInstanceOf(MemberServiceImpl.class);   /*같은 객체가 맞는지?*/
    }

    @Test
    @DisplayName("이름 없이 타입으로만 조회")
    void findBeanByType(){
        MemberService service = ac.getBean(MemberService.class);    /*인터페이스의 구현체가 대상*/
        assertThat(service).isInstanceOf(MemberServiceImpl.class);   /*같은 객체가 맞는지?*/
    }

    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByName2(){
        MemberService service = ac.getBean("memberService",MemberServiceImpl.class);
        assertThat(service).isInstanceOf(MemberServiceImpl.class);   /*같은 객체가 맞는지?*/
    }
    /*반환 타입(인스턴스 반환이 MemberServiceImpl을 반환하기 때문)으로 조회해도 됨.*/
    /*단 역할과 구분 -> 추상화에 의존해야하기 때문에 Service로 조회하는게 가장 좋긴 함.*/

    @Test
    @DisplayName("빈 이름으로 조회X")
    void findBeanByX(){
//        MemberService memberService = ac.getBean("test", MemberService.class);
        assertThrows(NoSuchBeanDefinitionException.class, () -> ac.getBean("test",MemberService.class));
        /*오른쪽 람다가 실행될 때 왼쪽 예외가 발생을 해야한다.*/
    }
}
