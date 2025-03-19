package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PrototypeTest {

    @Test
    void prototypeBeanFind(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PrototypeBean.class);
        System.out.println("find prototypeBean1 ");
        PrototypeBean bean = context.getBean(PrototypeBean.class);

        System.out.println("find prototypeBean2 ");
        PrototypeBean bean2 = context.getBean(PrototypeBean.class);

        System.out.println("prototypeBean1" + bean );
        System.out.println("prototypeBean2 " + bean2 );
        assertThat(bean).isNotSameAs(bean2);

        /*preDestory 메서드 호출이 되지 않음*/
        /*destory 호출할 일이 있는 경우, 직접 호출처리*/
//        bean.preDestroy();
//        bean.preDestroy();

        context.close();

    }


    @Scope("prototype")
    static class PrototypeBean {
        @PostConstruct
        public void postConstruct(){
            System.out.println("singleton post construct init");
        }

        @PreDestroy
        public void preDestroy(){
            System.out.println("singleton pre destroy");
        }
    }
}
