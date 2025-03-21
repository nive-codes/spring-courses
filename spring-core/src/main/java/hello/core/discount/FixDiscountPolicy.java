package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.stereotype.Component;

@Component
public class FixDiscountPolicy implements DiscountPolicy {

    private int discountFixAmount = 1000;

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP){     /*enum은 ==로 구분*/
            System.out.println("price" + price);
            System.out.println("discountFixAmount " + discountFixAmount);
            return discountFixAmount;
        }else{
            return 0;
        }


    }
}
