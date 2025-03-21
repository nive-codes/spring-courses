package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration  /*이 애노테이션 안에도 Component가 있음*/
@ComponentScan(
        basePackages = "hello.core", /*탐색 위치를 지정할 수 있고 {"hello.core", "hello.service"} 이렇게 여러 위치를 지정할 수 있음. 만약 지정하지 않으면 본인 위치에서부터 스캔 시작*/
        excludeFilters = @ComponentScan.Filter(type= FilterType.ANNOTATION, classes = Configuration.class)
        /*필터를 통해 Configuration 애노테이션이 붙은걸 뺀다(AutoAppConfig과 AppConfig 충돌 방지)*/
)  /*@Compoent 클래스를 찾아서 자동으로 spring bean으로 등록하는 애노테이션*/
public class AutoAppConfig {

}

