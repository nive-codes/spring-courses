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

//  하이버네이트6에서는 자동으로 중복 제거를 보정한다.(중복제거 최적화) 루트의 엔티티를 중복을 걸러줌
  public List<Order> findAllWithItem() {
    return em.createQuery(
            "select o from Order o " +
                    " join fetch  o.member m" +
                    " join fetch  o.delivery d" +
                    " join fetch  o.orderItems oi" +      //order 2개고 item이 2개일때, order row가 4개가 나옴.
                    " join fetch  oi.item i", Order.class
    )
//            .setFirstResult(1)    //1:다 조인의 경우 order의 기준 자체가 틀려져버리므로 페이징이 정상적으로 적용 않는다. group by,distinct 같은 쿼리 기준이 다름.
//            .setMaxResults(10)    //!!!!모든 데이터를 가지고 온 뒤에 메모리에서 페이징을 하므로 매우 위험하다.
            .getResultList();       //!!!!컬렉션 페치 조인(1:다)은 1개만 사용할 수 있고, 둘 이상에 페치조인을 해버리면 데이터가 부정합하게 조회될 수 있다.
  }

  public List<Order> findAllWithMemberDelivery() {
    return em.createQuery(
            "select o from Order o " +
                    " join fetch  o.member m" +
                    " join fetch  o.delivery d",Order.class
    )
            .getResultList();
  }

  public List<Order> findAllWithMemberDelivery(int offset, int limit) {
    return em.createQuery(
                    "select o from Order o " +
                            " join fetch  o.member m" +
                            " join fetch  o.delivery d",Order.class
            )
            .setFirstResult(offset)
            .setMaxResults(limit)
            .getResultList();
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
