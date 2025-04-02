package jpabook.jpashop.respository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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
//language=JPAQL
    String jpql = "select o From Order o join o.member m";
    boolean isFirstCondition = true;
//주문 상태 검색
    if (orderSearch.getOrderStatus() != null) {
      if (isFirstCondition) {
        jpql += " where";
        isFirstCondition = false;
      } else {
        jpql += " and";
      }
      jpql += " o.status = :status";
    }
//회원 이름 검색
    if (StringUtils.hasText(orderSearch.getMemberName())) {
      if (isFirstCondition) {
        jpql += " where";
        isFirstCondition = false;
      } else {
        jpql += " and";
      }
      jpql += " m.name like :name";
    }
    TypedQuery<Order> query = em.createQuery(jpql, Order.class)
            .setMaxResults(1000); //최대 1000건
    if (orderSearch.getOrderStatus() != null) {
      query = query.setParameter("status", orderSearch.getOrderStatus());
    }
    if (StringUtils.hasText(orderSearch.getMemberName())) {
      query = query.setParameter("name", orderSearch.getMemberName());
    }
    return query.getResultList();
  }

  /*fetch는 jpql만 있음.*/
  public List<Order> finAllWithMemberDelivery() {
    return em.createQuery(
            "select o from Order o " +
                    "join fetch o.member m " +
                    "join fetch o.delivery d", Order.class
    ).getResultList();
  }

  /*기본적으로 embedable이나 entity만 return 이 가능하다.*/
  public List<OrderSimpleQueryDto> findOrderDtos() {
    return em.createQuery("select new jpabook.jpashop.respository.OrderSimpleQueryDto(o.id,m.name,o.orderDate,o.status,d.address)" +
            " from Order o " +
            " join o.member m" +
            " join o.delivery d" , OrderSimpleQueryDto.class).getResultList();
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
