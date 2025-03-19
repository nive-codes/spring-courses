package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {
    @Test
    void prototypeFind(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);
        bean1.addCount();
        assertThat(bean1.getCount()).isEqualTo(1);

        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);
        bean2.addCount();
        assertThat(bean2.getCount()).isEqualTo(1);


    }

    @Test
    void singtonClientUsePrototype(){
        AnnotationConfigApplicationContext ac =
                new AnnotationConfigApplicationContext(ClientBean.class,PrototypeBean.class);

        ClientBean bean = ac.getBean(ClientBean.class);
        int count1 = bean.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean bean2 = ac.getBean(ClientBean.class);
        int count2 = bean.logic();
        assertThat(count2).isEqualTo(1);
    }
    //컴포넌트스캔 clientBean 생성 시점에 prototypebean이 이미 생성된 다음 주입이 되어, 계속 같은 객체를 사용한다.
    //
    @Scope("singleton")
    static class ClientBean {

//        @Autowired
//        private final PrototypeBean prototypeBean;

//      얘를 통해서 대신 prototype의 해당 빈을 찾아서 반환해줌.
        @Autowired
        private ObjectProvider<PrototypeBean> prototypeBeanProvider;

//        ObjectProvider는 ObjectFactory를 extends(상속)해서 활용되므로 여러 기능이 더 많음. 단 스프링에 의존
//        private ObjectFactory<PrototypeBean> prototypeBeanProvider;

//        @Autowired
//        public ClientBean(PrototypeBean prototypeBean) {
//            this.prototypeBean = prototypeBean;
//        }

        public int logic(){
            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }



    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount(){
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init(){
            System.out.println("prototypeBean init" + this.count);
        }

        @PreDestroy
        public void destroy(){
            System.out.println("prototypeBean destroy");
        }



    }
}
