package jpabook.jpashop.respository.order.query;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        List<OrderQueryDto> result = findOrders(); //1번
        result.forEach(o -> {
           List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId()); //jpql로 작성한걸 직접 넣어줌. n번
           o.setOrderItems(orderItems);
        });
        return result;
    }

    /*v5 oderitems를 한번의 쿼리로 다 가져옴*/
    /*쿼리 2번*/
    public List<OrderQueryDto> findAllByDto_optimization(){
        List<OrderQueryDto> result = findOrders();
        List<Long> orderIds = result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());

        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(orderIds);

        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;

    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
        List<OrderItemQueryDto> oderItems = em.createQuery("select new jpabook.jpashop.respository.order.query.OrderItemQueryDto(oi.order.id,i.name, oi.orderPrice, oi.count) " +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        Map<Long, List<OrderItemQueryDto>> orderItemMap = oderItems.stream()
                .collect(Collectors.groupingBy(OrderItemQueryDto -> OrderItemQueryDto.getOrderId()));
        return orderItemMap;
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery("select new jpabook.jpashop.respository.order.query.OrderItemQueryDto(oi.order.id,i.name, oi.orderPrice, oi.count)" +
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

    /*db에서 쿼리 한번으로 가져오면?*/
    public List<OrderFlatDto> findAllByDto_flat() {
        List<OrderFlatDto> resultList = em.createQuery(
                "select new " +
                        " jpabook.jpashop.respository.order.query.OrderFlatDto(o.id,m.name,o.orderDate,o.status,d.address,i.name, oi.orderPrice, oi.count)" +
                        " from Order o " +
                        "join o.member m " +
                        "join o.delivery d " +
                        "join o.orderItems oi " +
                        "join oi.item i", OrderFlatDto.class
        ).getResultList();
        return resultList;

    }

}
