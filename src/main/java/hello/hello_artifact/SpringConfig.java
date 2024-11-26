package hello.hello_artifact;

import hello.hello_artifact.respository.JdbcMemberRepository;
import hello.hello_artifact.respository.MemberRepository;
import hello.hello_artifact.respository.MemoryMemberRepository;
import hello.hello_artifact.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/*애노테이션 없이 직접 스자바코드로 스프링 빈 설정하는 방법*/
@Configuration
public class SpringConfig {

    /*Datasource를 bean으로 등록 후 주입*/
    private DataSource dataSource;
    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    /*spring bean을 직접 등록하겠다*/
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

//    @Bean
//    public MemberRepository memberRepository() {    /*interface니까 구현체는 MemoryMemberRepository*/
//        return new MemoryMemberRepository();
//    }

    /*이걸 나중에 DB에 저정하는 방식으로 하고 싶을 때 아래와 같이 구현체 부분만 바꿔서 Bean으로 등록하면 쉽게 변경이 가능하다.*/
    /**/
    @Bean
    public MemberRepository memberRepository() {
        return new JdbcMemberRepository(dataSource);
    }

    /*컴포넌트 스캔과 직접 작성의 차이*/
    /*Spring에서는 xml에서 사용*/
    /*최근에는 java code로 작성*/
    /*생성자를 통한 주입 생성 - 스프링팀에서 권장하는 방식*/
        /*기존 현재 controller에 있는 방식*/
    /*Seeter(수정자주입) 생성*/
    /*필드주입*/
        /*@Autowired MemberService service;*/

    /*필드 주입과 수정자 주입은 빈 생성 후 빈으로 등록할 객체를 찾아서 주입함 - 순환 참조*/
    /*순환 참조 시 생성되지 않는 빈을 참조하기 때문에 오류 발생-service의 insert가 동작하지 않는다던지*/
    /*final 선언할 수 없음 (변경의 여지가 있음)*/
    /*생성자 주입은 필드를 final로 선언해서 객체 불변성 보장을 할 수 있음*/

}
