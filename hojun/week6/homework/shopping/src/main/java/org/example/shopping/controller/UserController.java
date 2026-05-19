package org.example.shopping.controller;

import lombok.RequiredArgsConstructor;
import org.example.shopping.dto.user.UserRequestDto;
import org.example.shopping.dto.user.UserResponseDto;
import org.example.shopping.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Long> createUser(@RequestBody UserRequestDto request){
        Long userId = userService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getUsers(){
        List<UserResponseDto> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Void> updateUser (@PathVariable Long userId, @RequestBody UserRequestDto request){
        userService.updateUser(userId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
