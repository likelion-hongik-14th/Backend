package org.example.shopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.shopping.dto.UserRequestDto;
import org.example.shopping.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public String createUser(@RequestBody UserRequestDto request){
        userService.signup(request);
        return "사용자가 생성되었습니다.";
    }
}
