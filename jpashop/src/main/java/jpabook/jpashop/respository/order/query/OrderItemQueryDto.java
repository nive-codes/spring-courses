package jpabook.jpashop.respository.order.query;

import lombok.Data;

/**
 * @author hosikchoi
 * @class OrderItemQueryDto
 * @desc repository의 query용 dto의 orderitems용 dto
 * @since 2025-04-03
 */
@Data
public class OrderItemQueryDto {
    private Long orderId;
    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemQueryDto(Long orderId, String itemName, int orderPrice, int count) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
