package hello.hello_artifact.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data","hello!!");
        //templates 템플릿 밑에 hello를 찾아서 랜더링 후 브라우저 표시(viewResolver)가 처리해줌
        //스프링부트 템플릿 기본 viewName 매핑해줌
        return "hello";
    }


    /*템플릿엔진(view)를 가지고 조작하는 방식(직접 view 처리)*/
    @GetMapping("hello-thy")
    public String helloMvc(Model model, @RequestParam(value = "name", required = false, defaultValue = "DEFAULT VALUE") String name) {
        /*파라미터로 받아온 name 적용*/
        model.addAttribute("name",name);
        return "hello-thy";
    }

    /*API 방식 - return String*/
    /*responseBody : 템플릿(viewResolver)을 찾지말고 http 응답(HttpMessageConverter - 객에인 경우 그 중 JsonConverter)에 바로 데이터를 넣어서 넘기는 방식*/
    /*Http의 BODY에 문자 내용 직접 반환 처리*/
    /*Spring에서는 Jackson이 기본으로 탑재 되어 있음*/
    @GetMapping("hello-String")
    @ResponseBody
    public String helloApi(@RequestParam(value = "name리, required = false, defaultValue = DEFAULT VALUE") String name) {
        return "hello " + name;
    }

    /*API 방식 - return 객체*/
    /*API 방식 - xml 말고 api 대세가 된 이유 : 태그를 닫고 열고 2번 작업 + 무거움 + Map의 편리함이지*/
    /*json으로 바로 응답에 반환하는 것이 기본 정책*/
    /*참고 : 클라이언트의 Accept 헤더와 controller 반환 정보에 따라 httpMessageConverter가 선택됨.*/
    @GetMapping("hello-Api")
    @ResponseBody
    public Hello helloAPi(@RequestParam(value = "name",required = false) String name) {
        Hello hello = new Hello();

        hello.setName(name);

        return hello;
    }

        /*DTO - 자바 bean 표준규격 (메서드를 통해서 접근.)*/
        /*DTO - 프로퍼티 접근 방식(메서드를 통해서 접근.)*/
        static class Hello {
            private String name;

            public String getName(){
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
}
