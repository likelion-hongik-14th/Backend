package mutsa.w3Homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping("welcome")// /welcome이라는 주소를 다음 메소드로 처리하겠다.
    public String Hello(Model model) {
        //Model이라는 상자에 data라는 Key로 hello!!라는 값을 담는다
        model.addAttribute("data", "hello!!");
        return "hello";
    }

    @GetMapping("hello-mvc")
    public String HelloMvc(@RequestParam("name") String name, Model model) {
        model.addAttribute("name", name);
        return "hello-template";
    }

    @GetMapping("hello-string")
    @ResponseBody
    public String HelloString(@RequestParam("name") String name) {
        return "hello " + name;


    }

    @GetMapping("hello-api")
    @ResponseBody //JSON 형식으로 반환하는 게 기본
    public Hello HelloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
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
