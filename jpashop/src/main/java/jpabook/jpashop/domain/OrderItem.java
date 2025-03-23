package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;

/**
 * @author hosikchoi
 * @class OrderItem
 * @desc 주문상품 관련 Entity
 * @since 2025-03-23
 */
@Entity
public class OrderItem {

    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    //order item의 입장에서는 여러개이면서 item은 하나인 것이므로 ManyToOne으로
    @ManyToOne  //하나의 item에 여러가지의 order item이 존재하는 것.
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne      //하나의 order에 여러개의 orderItem이 있는 것.
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;//주문 가격
    private int count; //주문수량


}
