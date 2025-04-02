package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.respository.OrderRepository;
import jpabook.jpashop.respository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hosikchoi
 * @class OrderSimpleApiController
 * @desc 주문과 관련된 매우 단순한 rest api
 * xToOne
 * Order
 * Order -> Member
 * Order -> Delivery
 *
 * @since 2025-04-02
 */

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        /*!!!!!!!Order에 Member가 있고, Member에도 order가 있어서 무한루프 발생*/
        /*둘 중 하나를 order ignore해줘야함.(member)*/
        /*둘 중 하나를 order ignore해줘야함.(orderItem)*/
        /*둘 중 하나를 order ignore해줘야함.(delivery)*/
        /*hibernate5를 gradle에 작성한 후 빈으로 만들어서 처리하면 된다.*/
        /*어차피 entity를 return 하는 것은 진짜 안된다라고 인식할 것.*/
        List<Order> all = orderRepository.findAll(new OrderSearch());
//        for(Order order : all) {
//            order.getMember().getName();    //lazy 강제 초기화
//            order.getDelivery().getAddress();//강제 초기화(아무 값이나 가져오면 됨)
//        }
        return all;


    }
}
