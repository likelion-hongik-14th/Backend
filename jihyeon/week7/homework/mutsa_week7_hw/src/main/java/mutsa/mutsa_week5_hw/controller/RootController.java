package mutsa.mutsa_week5_hw.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class RootController {

    @GetMapping("/")
    public String home() {
        return "Spring Boot is running 🚀";
    }
}