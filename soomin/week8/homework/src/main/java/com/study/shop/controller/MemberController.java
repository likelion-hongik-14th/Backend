package com.study.shop.controller;

import com.study.shop.dto.member.LoginRequest;
import com.study.shop.dto.member.LoginResponse;
import com.study.shop.global.apiPayload.ApiResponse;
import com.study.shop.service.MemberService;
import com.study.shop.dto.member.MemberResponse;
import com.study.shop.dto.member.SignupRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Tag(name = "Member", description = "회원 API")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 이름을 입력받아 회원을 등록합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "회원가입 성공",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "MEMBER400_1 - 이미 가입된 이메일입니다.",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<MemberResponse>> signup(@Valid @RequestBody SignupRequest request) {
        MemberResponse response = memberService.signup(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.onSuccess("회원가입 성공" , response));
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호를 확인하여 로그인 성공 여부를 반환합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "로그인 성공",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "401",
                    description = "MEMBER401_1 - 이메일 또는 비밀번호가 일치하지 않습니다.",
                    content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = memberService.login(request);
        return ResponseEntity
                .ok(ApiResponse.onSuccess("로그인 성공", response));
    }

    @Operation(summary = "회원 정보 조회", description = "memberId를 기준으로 회원 정보를 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "회원 정보 조회 성공",
                    content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "MEMBER404_1 - 회원을 찾을 수 없습니다.",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MemberResponse>> getMember(@RequestParam Long memberId) {
        MemberResponse response = memberService.getMember(memberId);

        return ResponseEntity.ok(
                ApiResponse.onSuccess("회원 정보 조회 성공", response)
        );
    }

}
