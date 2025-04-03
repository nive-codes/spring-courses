package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.respository.OrderRepository;
import jpabook.jpashop.respository.OrderSearch;
import jpabook.jpashop.respository.order.query.OrderFlatDto;
import jpabook.jpashop.respository.order.query.OrderItemQueryDto;
import jpabook.jpashop.respository.order.query.OrderQueryDto;
import jpabook.jpashop.respository.order.query.OrderQueryRepository;
import jpabook.jpashop.service.query.OrderDto;
import jpabook.jpashop.service.query.OrderQueryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * @author hosikchoi
 * @class OrderApiController
 * @desc toMany 관련 테스트 Order Api 컨트롤러
 * @since 2025-04-03
 */

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    private final OrderQueryRepository orderQueryRepository;

    private final OrderQueryService orderQueryService;

    @GetMapping("/api/v1/orders")
    public List<Order> orderV1() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());

        //iter 이렇게 하면 자동으로 for로 만들어줌 위의 list를
        for (Order order : orders) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());

        }
        return orders;
    }

    //v2도 dto 하나만 감싸서 보내는거라서 좋은 예제가 아니다(dto안에 또 entity가 스펙 상 노출이 되어버리기 때문에)
    //귀찮더라도 orderItem도 데이터를 보낼때 감싸서 보내야된다.
    //dto는 프론트/백으로 나눠진 시점에서 화면과 같다고 생각하면 될 것 같다...
    //지연로딩이라 초기화할 때마다 사실상 쿼리가 계속 날아가서 성능 이슈가 발생.
    @GetMapping("/api/v2/orders")
    public  List<OrderDto>  orderV2() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());
        List<OrderDto> collect = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
        return collect;
    }

    /*fetch join v3*/
    /*하이버네이트5에서는 4개가 나와야되지만(initService 데이터 insert기준
    * 하이버네이트6에서는 자동으로 중복 제거를 보정한다.(중복제거 최적화) 루트의 엔티티를 중복을 걸러줌
    * v2와 차이는 그저 repository의 차이일뿐, 소스는 같다(성능 튜닝은 됐지만 소스를 많이 수정할 필요가 없음)
    * */
    @GetMapping("/api/v3/orders")
    public  List<OrderDto>  orderV3() {
//        List<Order> allWithItem = orderRepository.findAllWithItem();
//        List<OrderDto> collect = allWithItem.stream()
//                .map(o -> new OrderDto(o))
//                .collect(toList());
//        return collect;
        /*transaction이 적용된 service 리팩토링 처리  open-in-view가 false인 경우 에러 방지*/
        return orderQueryService.orderV3();
    }

    /*페이징이 가능한 3.1v*/
    /*toOne 관계의 애들은 한번의 fetch조인으로 데이터 가져오기*/
    /*v3보다 쿼리가 다소 조금 더 나가지만, 1:다 에 해당되는걸 n+1문제를 해결할 수 있다. 단 v3는 한개의 컬렉션이 있다면 fetch조인이 가능하다. 하지만 페이징이 안됨..*/
    /*v3가 페치조인이지만 중복된 데이터를 일단 애플리케이션으로 보내고 하이버네이트가 처리해주므로서, v3도 쿼리는 한번이지만 전송되는 데이터(용량)이 크다.
    * v3.1은 쿼리를 잘라서 v3보다 많이 보내지만 최적화되어(in절) 전송한다
    * application.yml 참조할 것.
    *
    * */
    @GetMapping("/api/v3.1/orders")
    public  List<OrderDto>  orderV3_page(
            @RequestParam(value = "offset", defaultValue = "0", required = false) int offset,
            @RequestParam(value = "limit", defaultValue = "10", required = false) int limit
                                              ) {
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset,limit);
        List<OrderDto> collect = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
        return collect;
    }

    //repository에서 바로 dto 반환
    @GetMapping("/api/v4/orders")
    public  List<OrderQueryDto>  orderV4() {
        return orderQueryRepository.finaOrderQueryDtos();
//        return null;
    }

    //repository에서 바로 dto 반환(쿼리 단 두번)
    @GetMapping("/api/v5/orders")
    public  List<OrderQueryDto>  orderV5() {
        return orderQueryRepository.findAllByDto_optimization();
//        return null;
    }

    //repository에서 바로 dto 반환 -> 플랫데이터 최적화
    @GetMapping("/api/v6/orders")
    public  List<OrderFlatDto>  orderV6() {
        List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat();      //중복으로 나온걸 아래 코딩을 통해 처리
        return flats;

//        List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat();
//        return flats.stream()
//                .collect(groupingBy(o -> new OrderQueryDto(o.getOrderId(),
//                                o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
//                        mapping(o -> new OrderItemQueryDto(o.getOrderId(),
//                                o.getItemName(), o.getOrderPrice(), o.getCount()), toList())
//                )).entrySet().stream()
//                .map(e -> new OrderQueryDto(e.getKey().getOrderId(),
//                        e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(),
//                        e.getKey().getAddress(), e.getValue()))
//                .collect(toList());


//        return null;
    }

    /*별도 클래스로 이전*/
//    @Data
//    static class OrderDto {
//        private Long orderId;
//        private String name;
//        private LocalDateTime orderDate;
//        private OrderStatus orderStatus;
//
//        private Address address;
////        private List<OrderItem> orderItems;   // dto로 감싸서 처리
//        private List<OrderItemDto> orderItems;
//
//        public OrderDto(Order order) {
//            orderId = order.getId();
//            name = order.getMember().getName();
//            orderDate = order.getOrderDate();
//            orderStatus = order.getStatus();
//
//            address = order.getDelivery().getAddress();
//            // orderItems = order.getOrderItems();      //entity라서 조회한게 없어서 바로 나오지 않음.
//            // order.getOrderItems().stream().forEach(o -> o.getItem().getName());   -> 프록시 초기화 후 다시 세팅
//            orderItems  = order.getOrderItems().stream()       ////똑같이 dto로 감싸서 필요한 데이터만 전달
//                    .map(o -> new OrderItemDto(o))
//                    .collect(toList());
//        }
//    }
//
//    @Data
//    static class OrderItemDto {
//        private String itemName;
//        private int orderPrice;
//        private int count;
//
//        public OrderItemDto(OrderItem orderItem) {
//            itemName = orderItem.getItem().getName();
//            orderPrice = orderItem.getOrderPrice();
//            count = orderItem.getCount();
//        }
//    }


}



