package jpabook.jpashop.service.query;

import jpabook.jpashop.domain.OrderItem;
import lombok.Data;

/**
 * @author hosikchoi
 * @class OrderItemDto
 * @desc [클래스 설명]
 * @since 2025-04-03
 */
@Data
public class OrderItemDto {

    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemDto(OrderItem orderItem) {
        itemName = orderItem.getItem().getName();
        orderPrice = orderItem.getOrderPrice();
        count = orderItem.getCount();
    }
}
