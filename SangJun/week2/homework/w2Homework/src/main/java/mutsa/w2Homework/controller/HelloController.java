package mutsa.w2Homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("welcome")// /welcome이라는 주소를 다음 메소드로 처리하겠다.
    public String Hello(Model model) {
        //Model이라는 상자에 data라는 Key로 hello!!라는 값을 담는다
        model.addAttribute("data", "hello!!");
        return "hello";
    }
}
