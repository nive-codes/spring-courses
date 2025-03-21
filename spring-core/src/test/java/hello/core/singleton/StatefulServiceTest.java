package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statuefulServiceSingeton(){

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService service = ac.getBean(StatefulService.class);
        StatefulService service2 = ac.getBean(StatefulService.class);

//       ThreadA : a 사용자가 10000원 주문
        int price1 = service.order("userA",10000);
//       ThreadB : b 사용자가 20000원 주문
        int price2 = service.order("userA",20000);

//       ThreadA : a 사용자 금액 확인
        int price =  service.getPrice();
        System.out.println("price = " + price);

        /*b가 중간에 20000이라고 했을 때 a 의 주문 금액도 20000원으로 처리되는 문제 발생*/
        Assertions.assertThat(price1).isEqualTo(10000);

    }

    static class TestConfig{
        @Bean
        public StatefulService statefulService(){
            return new StatefulService();
        }
    }


}