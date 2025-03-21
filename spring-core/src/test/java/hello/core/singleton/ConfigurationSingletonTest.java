package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {
    @Test
    @DisplayName("@Configuraion 테스트")
    void configurationTest(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        /*interface에 추가를 안해서 구체를 직접 가져와서 test*/
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);

        MemberRepository memoryMemberRepository1 = memberService.getMemberRepository();
        MemberRepository memoryMemberRepository2 = orderService.getMemberRepository();

        /*MemberRepository도 가져와서 확인*/
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        System.out.println("memberService -> memoryMemberRepository1 " + memoryMemberRepository1);
        System.out.println("orderService -> memoryMemberRepository2 " + memoryMemberRepository2);
        System.out.println("memberRepository " + memberRepository);

        Assertions.assertThat(memoryMemberRepository1).isSameAs(memberRepository);
        Assertions.assertThat(memoryMemberRepository2).isSameAs(memberRepository);


    }

    @Test
    @DisplayName("Configuration 자체를 뜯어서 확인-singleton")
    void configrationDeop(){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);
        System.out.println(bean);

    }

}
