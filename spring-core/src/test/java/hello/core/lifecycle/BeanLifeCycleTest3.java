package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest3 {

    @Test
    public void lifeCycleTest3(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig3.class);
        NetworkClient3 networkClient = ac.getBean(NetworkClient3.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig3{

        @Bean
        public NetworkClient3 networkClient(){
            NetworkClient3 networkClient = new NetworkClient3();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }

    }
}
