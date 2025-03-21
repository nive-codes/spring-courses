package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SingletonTest {
    @Test
    void scopeTestFind(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SingletonBean.class);
        SingletonBean singletonBean1 = context.getBean(SingletonBean.class);
        SingletonBean singletonBean2 = context.getBean(SingletonBean.class);
        System.out.println("singletonBean1 : "+singletonBean1);
        System.out.println("singletonBean2 : " + singletonBean2);
        assertThat(singletonBean1).isSameAs(singletonBean2);
    }

    @Scope("singleton")
    static class SingletonBean {
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
