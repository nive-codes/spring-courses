package jpabook.jpashop.service.query;

import jpabook.jpashop.api.OrderApiController;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.respository.OrderRepository;
import jpabook.jpashop.respository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author hosikchoi
 * @class OrderQueryService
 * @desc open view 옵션을 끄고 트랜잭션 안에서 제어하기 위해 만든 service 계층
 * @since 2025-04-03
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryService {

    private final OrderRepository orderRepository;

    /*v3 api를 트랜잭션 안에서 활용하기 위해 리팩토링 처리*/
    public List<OrderDto> orderV3(){

        List<Order> allWithItem = orderRepository.findAllWithItem();
        List<OrderDto> collect = allWithItem.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
        return collect;
    }

}
