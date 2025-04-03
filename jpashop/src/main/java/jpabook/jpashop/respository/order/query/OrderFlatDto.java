package jpabook.jpashop.respository.order.query;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author hosikchoi
 * @class OrderFlatDto
 * @desc v6용 dto 모두 조인해서 한방 쿼리로 가지고 오도록(order, oderitem, item...)
 * @since 2025-04-03
 */
@Data
public class OrderFlatDto {

  private Long orderId;
  private String name;
  private LocalDateTime orderDate;
  private OrderStatus orderStatus;
  private Address address;


  private String itemName;
  private int orderPrice;
  private int count;

  public OrderFlatDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address, String itemName, int orderPrice, int count) {
    this.orderId = orderId;
    this.name = name;
    this.orderDate = orderDate;
    this.orderStatus = orderStatus;
    this.address = address;
    this.itemName = itemName;
    this.orderPrice = orderPrice;
    this.count = count;
  }
}
