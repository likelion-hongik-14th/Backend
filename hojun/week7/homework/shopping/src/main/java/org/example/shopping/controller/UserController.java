package org.example.shopping.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.shopping.dto.user.UserRequestDto;
import org.example.shopping.dto.user.UserResponseDto;
import org.example.shopping.global.apiPayload.ApiResponse;
import org.example.shopping.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User API", description = "유저 도메인 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "이름, 이메일, 비밀번호를 전달받아 유저를 생성한다")
    @PostMapping
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "CREATED", description = "회원가입이 완료되었습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<Long>> createUser(@RequestBody UserRequestDto request){
        Long userId = userService.signup(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess("회원가입이 완료되었습니다.", userId));
    }

    @Operation(summary = "전체 회원 조회")
    @GetMapping
    @ApiResponses(value = {@io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SUCCESS", description = "전체 회원 조회가 완료되었습니다.", content = @Content(mediaType = "application/json"))})
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> getUsers(){
        List<UserResponseDto> users = userService.getUsers();
        return ResponseEntity
                .ok(ApiResponse.onSuccess("전체 회원 조회가 완료되었습니다.", users));
    }

    @Operation(summary = "회원 정보 수정", description = "이름, 이메일 혹은 비밀번호를 수정한다")
    @PatchMapping("/{userId}")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SUCCESS", description = "회원 정보가 수정되었습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_NOT_FOUND", description = "존재하지 않는 회원입니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<Void>> updateUser (@PathVariable Long userId, @RequestBody UserRequestDto request){
        userService.updateUser(userId, request);
        return ResponseEntity
                .ok(ApiResponse.onSuccess("회원 정보가 수정되었습니다."));
    }

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/{userId}")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "SUCCESS", description = "회원 탈퇴가 완료되었습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_NOT_FOUND", description = "존재하지 않는 회원입니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return ResponseEntity
                .ok(ApiResponse.onSuccess("회원 탈퇴가 완료되었습니다."));
    }
}