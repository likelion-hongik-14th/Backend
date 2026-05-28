package com.app.mutsa_shoppingmall.controller;

import com.app.mutsa_shoppingmall.dto.ApiResponse;
import com.app.mutsa_shoppingmall.dto.UserDto;
import com.app.mutsa_shoppingmall.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping
    public ResponseEntity<ApiResponse<UserDto.Response>> register(
            @RequestBody UserDto.RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess("회원가입 성공", userService.register(request)));
    }

    // 단건 조회
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDto.Response>> getUser(
            @PathVariable Long userId) {
        return ResponseEntity.ok(ApiResponse.onSuccess("회원 조회 성공", userService.getUser(userId)));
    }

    // 정보 수정
    @PatchMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserDto.Response>> updateUser(
            @PathVariable Long userId,
            @RequestBody UserDto.UpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.onSuccess("회원 정보 수정 성공", userService.updateUser(userId, request)));
    }

    // 회원 탈퇴
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.onSuccess("회원 탈퇴 성공"));
    }
}