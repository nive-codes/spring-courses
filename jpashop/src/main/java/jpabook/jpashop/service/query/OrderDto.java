package jpabook.jpashop.service.query;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
    * @class OrderDto
    * @author hosikchoi
    * @since 2025-04-03
    * @desc [클래스 설명]
    */
@Data
public class OrderDto {


    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;

    private Address address;
    //        private List<OrderItem> orderItems;   // dto로 감싸서 처리
    private List<OrderItemDto> orderItems;

    public OrderDto(Order order) {
      orderId = order.getId();
      name = order.getMember().getName();
      orderDate = order.getOrderDate();
      orderStatus = order.getStatus();

      address = order.getDelivery().getAddress();
      // orderItems = order.getOrderItems();      //entity라서 조회한게 없어서 바로 나오지 않음.
      // order.getOrderItems().stream().forEach(o -> o.getItem().getName());   -> 프록시 초기화 후 다시 세팅
      orderItems  = order.getOrderItems().stream()       ////똑같이 dto로 감싸서 필요한 데이터만 전달
              .map(o -> new OrderItemDto(o))
              .collect(toList());
    }

}
