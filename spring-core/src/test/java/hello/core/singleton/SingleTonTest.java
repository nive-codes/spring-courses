package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class SingleTonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer(){
        AppConfig appConfig = new AppConfig();
        /*조회 : 호출할 때마다 객체 생성하는지 확인*/
        MemberService memberService = appConfig.memberService();

        MemberService memberService2 = appConfig.memberService();

//        System.out.println("memberService" + memberService);
//        System.out.println("memberService2" + memberService2);

        /*서비스는 두개가 다르다는 것을 자동화 증명*/
        assertThat(memberService).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest(){
//        new SingletonServiceTest();   /*컴파일 오류*/
        SingletonServiceTest singletonServiceTest = SingletonServiceTest.getInstance();
        SingletonServiceTest singletonServiceTest2 = SingletonServiceTest.getInstance();

        System.out.println(singletonServiceTest);
        System.out.println(singletonServiceTest2);
        /*같은 객체*/
        assertThat(singletonServiceTest).isSameAs(singletonServiceTest2);
        /*same : ==*/
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer(){
//        AppConfig appConfig = new AppConfig();
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService1 = ac.getBean("memberService",MemberService.class);
        MemberService memberService2 = ac.getBean("memberService",MemberService.class);

        System.out.println(memberService1);
        System.out.println(memberService2);

        assertThat(memberService1).isSameAs(memberService2);
    }
}
