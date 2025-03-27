package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.respository.ItemRepository;
import jpabook.jpashop.respository.MemberRepository;
import jpabook.jpashop.respository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author hosikchoi
 * @class OrderService
 * @desc 주문 서비스
 * @since 2025-03-25
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

  private final OrderRepository orderRepository;
  private final MemberRepository memberRepository;
  private final ItemRepository itemRepository;


  /**
   * 주문
   */
  @Transactional
  public Long order(Long memberId, Long itemId, int count) {
    Member member = memberRepository.findOne(memberId);
    Item item = itemRepository.findOne(itemId);

    //배송정보 생성
    Delivery delivery = new Delivery();
    delivery.setAddress(member.getAddress());

    //주문 상품 생성
    OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count); //생성 메서드를 통해 생성됨

    Order order = Order.createOrder(member, delivery, orderItem);
//    Order order2 = Order.createOrder(member, delivery, orderItem, orderItem, orderItem); 여러개 item을 넘길 수도 있음.

    //주문 저장
    orderRepository.save(order);  //Order 안에 보면 cascade를 통해 CascadeType이 ALL이면 persist를 해줌(딜리버리, orderItem)
                                  //단 이런 경우에만 써야 함. Order -> OderItem -> Delivery와 같은 하나의 비즈니스로직에서의 상속, 참조하는 경우에만 하는게 편하다... 주의해서 쓸 것.

    return order.getId(); //영속성에 따라서 id는 이미 만들어진 상태임.


  }

  /**
   * 주문 취소
   */
  @Transactional
  public void cancelOrder(Long orderId) {
    Order order = orderRepository.findOne(orderId);
    order.cancel();
    //쿼리에 파라미터 넣어서 돌리지 않아도 된다!!!!!!!!!!!!!!!!!!! JPA는 service에서 다 짜지 않아도 된다!
    //entity안에 있는 데이터만 바꾸면 변경내역감지가 일어나면서 db에 쿼리가 직접 날아간다!!!!!!!!!!!!!!!!!!!!!!
    //ORM을 쓰면 이렇게 중요 비즈니스 로직이 entity안에 있는게 도메인 모델 패턴
    //기존 mybatis에서 쓰는 방식(service에 로직이 있는 경우) 랜잭션 스크립트 패턴
    //문맥에 맞게 잘 써야함. 프로젝트에 따라 양립되므로 판단 후 활용할 것.

  }

  /**
   * 검색
   */
//  public List<Order> findOrders(OrderSearch orderSearch){
//    return orderRepository.findAll();
//  }



}
