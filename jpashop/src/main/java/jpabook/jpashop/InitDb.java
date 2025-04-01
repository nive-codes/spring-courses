package jpabook.jpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author hosikchoi
 * @class InitDb
 * @desc jpa api 개발 샘플 데이터 처리용
 * userA
 *  JPA1 BOOK
 *  JPA2 BOOK
 * userB
 *  Spring1 BOOK
 *  Spring2 BOOK
 * @since 2025-04-01
 */

@Component
@RequiredArgsConstructor
public class InitDb {

  private final InitService initService;


  @PostConstruct
  public void init(){
    initService.dbInit1();
    initService.dbInit2();
  }


  /*별도 bean으로 등록 후 사용해야된다.*/
  @Component
  @Transactional
  @RequiredArgsConstructor
  static class InitService {
    private final EntityManager em;

    public void dbInit1(){

      Member member = createMember("userA","서울","1111","111111");
      em.persist(member);


      Book book1 = new Book();
      book1.setName("JPA1 BOOK");
      book1.setPrice(10000);
      book1.setStockQuantity(100);
      em.persist(book1);

      Book book2 = new Book();
      book2.setName("JPA2 BOOK");
      book2.setPrice(20000);
      book2.setStockQuantity(100);
      em.persist(book2);

      OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
      OrderItem orderItem2 = OrderItem.createOrderItem(book2, 10000, 2);

      Delivery delivery = new Delivery();
      delivery.setAddress(member.getAddress());

      Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
      em.persist(order);


    }


    public void dbInit2(){

      Member member = createMember("userB","진주","2","222222");
      em.persist(member);

      Book book1 = new Book();
      book1.setName("SPRING1 BOOK");
      book1.setPrice(10000);
      book1.setStockQuantity(100);
      em.persist(book1);

      Book book2 = new Book();
      book2.setName("SPRING2 BOOK");
      book2.setPrice(20000);
      book2.setStockQuantity(100);
      em.persist(book2);

      OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
      OrderItem orderItem2 = OrderItem.createOrderItem(book2, 10000, 2);

      Delivery delivery = new Delivery();
      delivery.setAddress(member.getAddress());

      Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
      em.persist(order);


    }
    private Member createMember(String userA, String address, String street, String zipcdoe ){
      Member member = new Member();
      member.setName(userA);
      member.setAddress(new Address(address,street,zipcdoe));
      return member;
    }
  }


}
