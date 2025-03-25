package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

/**
 * @author hosikchoi
 * @class OrderItem
 * @desc 주문상품 관련 Entity
 * @since 2025-03-23
 */
@Entity
@Getter @Setter
public class OrderItem {

    @Id @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    //order item의 입장에서는 여러개이면서 item은 하나인 것이므로 ManyToOne으로
    @ManyToOne(fetch = FetchType.LAZY)  //가급적이면 XToOne은 쓰지말고 꼭 지연로딩(LAZY)로 처리)  //하나의 item에 여러가지의 order item이 존재하는 것.
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)  //가급적이면 XToOne은 쓰지말고 꼭 지연로딩(LAZY)로 처리)      //하나의 order에 여러개의 orderItem이 있는 것.
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;//주문 가격
    private int count; //주문수량

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);    //넘어온 것만큼 재고 마이너스 처리
        return orderItem;

    }

    //==비즈니스 로직==//
    //재고 원복
    public void cancel() {
        getItem().addStock(count);
    }

    //==조회 로직==//

    /**
     * 전체 가격 조회
     * @return
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
