package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
//@Qualifier("mainDiscountPolicy")  /*안의 문자열 오류, 오타를 컴파일 단계에서 잡을 수 없기 때문에 만들어준 애노테이션을 활용*/
@MainDiscountPolicy
/*새로운 할인 정책 추가를 위해 구현체 생성*/
public class RateDiscountPolicy implements DiscountPolicy {

    private int discountPercent = 10;

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade() == Grade.VIP){     /*enum은 ==로 구분*/
            return price * discountPercent / 100;
        }else{
            return 0;
        }


    }
}
