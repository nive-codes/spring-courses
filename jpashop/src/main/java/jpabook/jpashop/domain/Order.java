package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

/*API의 경우, Entity를 꼭 리턴 시키면 안되고, api 스펙이 변경되므로 외부에는 절대 entity를 노출시키지 말자. 중요하다.*/
@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //직접 생성하지말고 생성 메서드를 통해 생성
public class Order {

    @Id @GeneratedValue
    @Column(name="order_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)  //가급적이면 XToOne은 쓰지말고 꼭 지연로딩(LAZY)로 처리)
    @JoinColumn(name = "member_id")
    /*외래키는 orders 테이블에 있고, Order Entity가 또 Member를 가지고 있기 때문에 얘를 연관관계의 주인으로 규악해서 사용*/
    private Member member;

    // @BatchSize(size = 1000) 이런식으로 배치사이즈를 직접 조절할 수도 있다.(컬렉션일땐)
    @OneToMany(mappedBy = "order", cascade=CascadeType.ALL) //order가 persist?될때 orderItems도 함께 persist됨. 각각 persist를 해줘야되는데 그럴 필요가 없는 것.
    private List<OrderItem> orderItems = new ArrayList<>(); //바로 초기화 (new)로 하면 NullPointerException에서 안전하고, 또 컬렉션으로 감싼다 하이버네이트가

    // 1:1 관계에서는 order에 외래키를 주로 두는 편(오더에서 딜리버리를 찾기 때문)
    // 연관관계의 주인 역시 order있는 delivery를 주인으로 두기
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)  //가급적이면 XToOne은 쓰지말고 꼭 지연로딩(LAZY)로 처리)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    //하이버네이트가 직접 지원해줌
    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)   //ORDINAL말고 꼭 STRING으로 해야되고, 순서 밀리는게 없음.
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    /*=연관관계 편의 메서드 : 앙뱡향일때 쓰는게 좋고, 양쪽 세팅을 하는걸 한 코드로 해겷하는 것.=*/
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }


    /*==생성 매서드==*/
    /*앞으로 이것만 찾아서 생성하는 로직 관련 변경을 하면 됨.*/
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER); //처음엔 ORDER상태로
        order.setOrderDate(LocalDateTime.now());    //현재 시간대로
        /*연관관계대로 전부다 세팅 후 return(생성)*/
        return order;
    }

    //==비즈니스 로직==//
    /*
    * 주문 취소
    * */
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }

    //==조회 로직==//
    /**
     * 전체 가격 주문 조회
     * @return
     */
    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }

        return totalPrice;
    }

}
