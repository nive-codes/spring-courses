package jpabook.jpashop.respository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hosikchoi
 * @class OrderRepository
 * @desc 주문 repository
 * @since 2025-03-25
 */
@Repository
@RequiredArgsConstructor
public class OrderRepository {

  private final EntityManager em;

  public void save(Order order) {
    em.persist(order);
  }

  public Order findOne(Long id) {
    return em.find(Order.class, id);
  }

  public List<Order> findAll(OrderSearch orderSearch) {
//    return em.createQuery("select o from Order o join o.member m" +
//            " where o.status = :status " +
//            "and m.name like :name", Order.class)
//            .setParameter("status",orderSearch.getOrderStatus())
//            .setParameter("name",orderSearch.getMemberName())
//            .setMaxResults(1000).getResultList();    //단 파라미터가 없는 경우에는??

    // jpql을 사용해서 동적을 구현하고자 하는 경우, 아래처럼 코드가 복잡해지는 경우가 발생 -> 따라서 mybatis, querydsl등을 사용)
    String jpql = "select o from Order o join.o.member m";  //문자열 오류.... 발생 여지가 존재함.
    boolean isForstCondition = true;
    if(orderSearch.getOrderStatus() != null) {
      if(isForstCondition) {
        jpql += " where";
        isForstCondition = false;
      }else{
        jpql += " and ";
      }
      jpql += " o.status = :status";
    }


    if(StringUtils.hasText(orderSearch.getMemberName())) {
      if(isForstCondition) {
        jpql += " where";
        isForstCondition = false;
      }else{
        jpql += " and ";
      }
      jpql += " o.name like :name";
    }
    TypedQuery<Order> query = em.createQuery(jpql, Order.class);
    query.setMaxResults(1000);

    if(orderSearch.getOrderStatus() != null) {
      query = query.setParameter("status", orderSearch.getOrderStatus());
    }
    if(StringUtils.hasText(orderSearch.getMemberName())) {
      query = query.setParameter("name", orderSearch.getMemberName());
    }
    return query.getResultList();
  }


  //jpql을 자바코드로 작성할때 표준으로 제공해주는 것이 있음

  /**
   * JPA Criteria
   * 실무를 안쓰는게 좋다........ jpa 표준 스펙이긴 하지만 안쓴다.
   * 이걸 보고 단순히 무슨 쿼리인지 이해하기가 힘들다(유지보수성 낮음)
   * @param orderSearch
   * @return
   */
//  public List<Order> findAllByCriteria(OrderSearch orderSearch) {
//    CriteriaBuilder cb = em.getCriteriaBuilder();
//    CriteriaQuery<Order> cq = cb.createQuery(Order.class);
//    Root<Order> o = cq.from(Order.class);
//    o.join("member", JoinType.INNER);
//    List<Predicate> criteria = new ArrayList<>();
//
//    //주문 상태 검색
//    if(orderSearch.getOrderStatus() != null) {
//      Predicate status = cb.equal(o.get("status"),orderSearch.getOrderStatus());
//      criteria.add(status);
//    }
//  ......
//  }


}
