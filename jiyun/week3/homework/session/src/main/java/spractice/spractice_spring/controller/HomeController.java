package spractice.spractice_spring.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home(){
        return "home"; // 템플릿 엔진의 home.html을 검색
    }
}
