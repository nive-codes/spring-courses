package hello.hello_artifact.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    /*reqeustMapping : 모든 mapping에 대해 요청이 처리 됩니다(get, post, put, delete...)*/
    /*getMapping, postMapping 으로 나눠서 활용을 해야 기타 메소드에 대한 취약점 방지가 가능하므로 가급적이면 나눠서 활용 바랍니다.*/
    @GetMapping("/")
    public String home() {
        return "home";
        /*매핑이 / 이렇게 되어 있는 경우 정적 컨텐츠보다 우선 순위를 지닌다.*/
    }
}
