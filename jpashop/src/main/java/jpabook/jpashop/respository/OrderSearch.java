package jpabook.jpashop.respository;

import jpabook.jpashop.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class OrderSearch {

    private String memberName;  //회원 이름
    private OrderStatus orderStatus;    //주문상태[ORDER, CANCEL]
}
