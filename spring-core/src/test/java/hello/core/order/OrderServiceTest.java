package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderServiceTest {

//    MemberService memberService = new MemberServiceImpl();
//    OrderService orderService = new OrderServiceImpl();
    MemberService memberService;
    OrderService orderService;

    /*사용할떄마다 돌도록 처리*/
    /*같은 변수 참조할때 오류 생길 수도 있으므로*/
    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }


    @Test
    void createOrder(){
//        long memberId = null;   null이 안되므로 refer타입으로 가급적 생성하는 편
        Long memberId = null;
        Member member = new Member(memberId,"memberA", Grade.VIP);

        memberService.join(member);

        Order order = orderService.createOrder(memberId,"itemName",10000);
        Assertions.assertThat(9000).isEqualTo(order.calculatePrice());
        Assertions.assertThat(1000).isEqualTo(order.getDiscountPrice());
    }
}