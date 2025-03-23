package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hosikchoi
 * @class Order
 * @desc 오더(주문) 객체
 * @since 2025-03-23
 */

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name="order_id")
    private long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    /*외래키는 orders 테이블에 있고, Order Entity가 또 Member를 가지고 있기 때문에 얘를 연관관계의 주인으로 규악해서 사용*/
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    // 1:1 관계에서는 order에 외래키를 주로 두는 편(오더에서 딜리버리를 찾기 때문)
    // 연관관계의 주인 역시 order있는 delivery를 주인으로 두기
    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    //하이버네이트가 직접 지원해줌
    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)   //ORDINAL말고 꼭 STRING으로 해야되고, 순서 밀리는게 없음.
    private OrderStatus orderStatus; //주문상태 [ORDER, CANCEL]
}
