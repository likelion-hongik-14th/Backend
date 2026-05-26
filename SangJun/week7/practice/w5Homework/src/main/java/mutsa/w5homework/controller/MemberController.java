package mutsa.w5homework.controller;

import jakarta.validation.Valid;
import mutsa.w5homework.dto.MemberCreateRequestDto;
import mutsa.w5homework.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import mutsa.w5homework.global.apiPayload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mutsa.w5homework.service.MemberService;

import static mutsa.w5homework.global.apiPayload.ApiResponse.onSuccess;

@RestController
@RequestMapping("/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

        @PostMapping
    public ApiResponse<MemberResponseDto> createMember(@Valid @RequestBody MemberCreateRequestDto requestDto) {
        MemberResponseDto responseDto = memberService.createMember(requestDto);
            return onSuccess("회원가입에 성공했습니다.", responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> getMember(@PathVariable Long id) {
        MemberResponseDto responseDto = memberService.getMember(id);
        return ResponseEntity.ok(responseDto);
    }
}
