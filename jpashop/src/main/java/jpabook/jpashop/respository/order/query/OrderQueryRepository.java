package jpabook.jpashop.respository.order.query;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hosikchoi
 * @class OrderQueryRepository
 * @desc v4용 order 용 쿼리를 dto로 return 하기 위한 repository
 * @since 2025-04-03
 */
@RequiredArgsConstructor
@Repository
public class OrderQueryRepository {
    private final EntityManager em;

    public List<OrderQueryDto> finaOrderQueryDtos(){

        List<OrderQueryDto> result = findOrders();
        result.forEach(o -> {
           List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId()); //jpql로 작성한걸 직접 넣어줌.
           o.setOrderItems(orderItems);
        });
        return result;
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery("select new jpabook.jpashop.respository.order.query.OrderItemQueryDto(oi.order.id,i.name, oi.orderPrice, oi.count) " +
                " from OrderItem oi"+
                " join oi.item i" +
                " where oi.order.id = :orderId", OrderItemQueryDto.class)
        .setParameter("orderId", orderId)
                .getResultList();

    }

    private List<OrderQueryDto> findOrders() {
        return em.createQuery(
                "select new jpabook.jpashop.respository.order.query.OrderQueryDto(o.id,m.name,o.orderDate,o.status,d.address) from Order o " +
                        " join o.member m " +
                        " join o.delivery d", OrderQueryDto.class).getResultList();
    }


}
