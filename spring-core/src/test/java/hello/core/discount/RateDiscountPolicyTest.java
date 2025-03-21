package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {

    RateDiscountPolicy rateDiscountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인이 적용 되어야 한다.") /*junit5부터 적용*/
    void vip_o(){
        //given
        Member memberA = new Member(1L, "memberA", Grade.VIP);

        //when
        int discount = rateDiscountPolicy.discount(memberA,10000);
        //then
        Assertions.assertThat(discount).isEqualTo(1000);
        /*천원이 할인이 되는지?*/
    }

    @Test
    @DisplayName("VIP가 아니면 10% 할인이 적용되지 않아야 한다.")
    void vip_x(){
        //given
        Member memberA = new Member(1L, "memberA", Grade.BASIC);

        //when
        int discount = rateDiscountPolicy.discount(memberA,10000);
        //then
//        Assertions.assertThat(discount).isEqualTo(1000);
        /*천원이 할인이 되는지?*/

        Assertions.assertThat(discount).isEqualTo(0);
    }

}