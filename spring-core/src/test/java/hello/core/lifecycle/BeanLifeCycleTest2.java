package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest2 {

    @Test
    public void lifeCycleTest2(){
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig2.class);
        NetworkClient2 networkClient = ac.getBean(NetworkClient2.class);
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig2{
        @Bean(initMethod = "init", destroyMethod = "close")
        public NetworkClient2 networkClient(){
            NetworkClient2 networkClient = new NetworkClient2();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }

    }
}
