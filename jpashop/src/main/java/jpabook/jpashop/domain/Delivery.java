package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hosikchoi
 * @class Delivery
 * @desc [클래스 설명]
 * @since 2025-03-23
 */
@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    //거울(order 객체에 있는 delivery
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)//가급적이면 XToOne은 쓰지말고 꼭 지연로딩(LAZY)로 처리)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)   //ORDINAL말고 꼭 STRING으로 해야되고, 순서 밀리는게 없음.
    private DeliveryStatus status; // READY,COMP

}

