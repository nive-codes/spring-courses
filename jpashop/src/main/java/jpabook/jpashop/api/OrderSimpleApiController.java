package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.respository.OrderRepository;
import jpabook.jpashop.respository.OrderSearch;
import jpabook.jpashop.respository.OrderSimpleQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {

        /*order 2개*/
        /*근데 첫번쨰 order에서 getMember.getName할떄 Member db 조회*/
        /*근데 첫번쨰 order에서 delivery 조회할떄 delivery db 조회*/

        /*근데 두번째 order에서 getMember.getName할떄 Member db 조회*/
        /*근데 두번째 order에서 delivery 조회할떄 delivery db 조회*/
        /*총 쿼리 5번 조회됨.*/
        /*N+1의 문제가 발생하게 됨. -> 오더1건 + 회원 N + 배송 N*/
        List<Order> orders = orderRepository.findAll(new OrderSearch());

        /*1-1. 문제는 stream으로 루프 돌릴때*/
        List<SimpleOrderDto> collect = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
        return collect;
    }

    /*100%  패치조인 이해할 것.*/
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderRepository.finAllWithMemberDelivery();

        return orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());

    }

    /*100% jpa로 dto로 바로 조회*/
    /*repository를 보면 내가 직접 원하는거만 짰으므로 직접 가져옴.*/
    /*fetch는 같으나 필요없는 값도 select해오는 문제가 있음.*/
    /*v3와 v4는 트레이드 오프가 있음*/
    /*v3는 재사용성이 있음 order에 필요한 값만(member, address만 활용해서 가져옴). entity이므로 데이터를 바꾸면 마지막 flush가 일어날때 트랜잭션-db반영됨.*/
    /*v4는 딱 OrderSimpleQueryDto만 사용할때만 활용. 로직을 재활용할 수 없음. 단 v4가 성능은 아주 조금 더 좋다. 단 dto이므로 비즈니스 로직을 사용할 수 없다.*/
    /*api 스펙에 맞춰서 repository를 짠거라서, repository는 entity를 관리하는건데.. 고민해볼만하다. repository가 api 스펙에 의존하는 형태가 된다.*/
    /*v4를 써야될때는 가급적 별도 패키지에 repository와 dto를 만들어서 controller에서 주입받아서 활용하자. repository는 가급적 entity를 핸들링 하는 용도로만.*/
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderRepository.findOrderDtos();

    }



    @Data
    static class SimpleOrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private Address address;
        private OrderStatus orderStatus;

        public SimpleOrderDto(Order order){
            /*1-2. lazy 초기화를 통해 영속성 컨텍스트가 db에서 데이터를 가져온다. 즉 order 조회하고 stream 되면서 해당 dto 세팅할때 db에 붙어버리면서 db조회가 많아진다.*/
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();

        }
    }


}
