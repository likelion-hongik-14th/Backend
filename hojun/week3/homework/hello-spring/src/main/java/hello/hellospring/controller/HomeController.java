package hello.hellospring.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< HEAD

=======
>>>>>>> 0f9a2779c2d81ba69f2b0ee63764d0ea67e5c9b9
@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "home";
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 0f9a2779c2d81ba69f2b0ee63764d0ea67e5c9b9
