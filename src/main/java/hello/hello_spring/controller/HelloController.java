package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 컨트롤러는 요청을 받는다.
 */
@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "Hello!!");
        return "hello"; // resources/templates/hello.html
    }

    @GetMapping("hello-mvc")
    /**
     * MVC Template 방식
     * 1. 웹 브라우저 -> 스프링 부트 (내장 톰켓 서버) -> 스프링 컨테이너
     *  - helloController -> viewResolver
     */
    public String helloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    /**
     * @ResponseBody의 사용 원리
     * 1. 웹 브라우저 -> 스프링 부트(내장 톰켓 서버) -> 스프링 컨테이너
     *  - localhost:8080/hello-api
     *  - helloController -> HttpMessageConverter
     *      - Controller가 객체를 반환
     *      - Converter가 객체를 클라이언트에 전달 가능한 형태로 변환
     *  - 객체가 오면, "Json 방식"으로 데이터를 만들어서 반환하는 것이 Default
     *      - 기본 문자처리 : StringHttpMessageConverter
     *      - 기본 객체처리 : MappingJackson2HttpMessageConverter
     */
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);

        // 웹 브라우저에 객체로 전달
        return hello;
    }

    static class Hello {
        private String name;

        // 자바 Bean 표준 규약
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
