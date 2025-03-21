package hello.core.beanFind;

import hello.core.AppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextSameFindTest{

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SameBeanConfig.class);

    @Test
    @DisplayName("타입으로 조회 시 같은 타입이 둘 있으면 중복 오류 발생")
    void findType(){
//        MemberRepository bean = ac.getBean(MemberRepository.class); /*중복된 오류 에러 발생*/
        assertThrows(NoUniqueBeanDefinitionException.class,()->ac.getBean(MemberRepository.class));
    }

    @Test
    @DisplayName("타입으로 조회 시 같은 타입이 둘 있으면 빈 이름을 지정하면 된다.")
    void findBeanByName(){
        MemberRepository bean = ac.getBean("memberRepository1",MemberRepository.class); /*이름 지정 시 1개 가져옴*/
        assertThat(bean).isInstanceOf(MemberRepository.class);
    }

    @Test
    @DisplayName("특정 타입을 모두 조회하기")
    void findAllBeanByType(){
        Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
        for(String key : beansOfType.keySet()){
            System.out.println("key = " + key + " value = " + beansOfType.get(key));
        }

        System.out.println("beansOfType = " + beansOfType);
        assertThat(beansOfType.size()).isEqualTo(2);    /*2개 인지*/



    }

    /*중복 테스트를 해보기 위한 configuration*/
    @Configuration
    static class SameBeanConfig{    /*static class 안에 class를 생성하는건 이 클래스에서만 사용*/
        @Bean
        public MemberRepository memberRepository1(){
        return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2(){
        return new MemoryMemberRepository();
        }
    }
}
