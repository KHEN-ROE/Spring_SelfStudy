package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model) {
        // model에 key, value 담아서 hello.html 뷰 리턴
        model.addAttribute("data", "hello!");
        return "hello"; // 해당 요청을 처리하여 hello.html에 데이터를 담아서 클라이언트에게 리턴한다.
                        // 따라서 클라이언트는 localhost:8080/hello 페이지에 가야 요청한 데이터를 볼 수 있다.

    }

    @GetMapping("hello-mvc")
    public String helloMvc(@RequestParam(value = "name", required = false) String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-String")
    @ResponseBody // http(헤더 -바디)의 바디 부분을 직접 리턴하겠다는 뜻. view 없이 문자 그대로 리턴
    public String helloString(@RequestParam("name") String name) {
        return "hello " + name;
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name")String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello; // 객체는 JSON방식으로 데이터를 만들어서 리턴
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
