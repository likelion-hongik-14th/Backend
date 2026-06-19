package mutsa.w5homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Member", description = "Member API")
public class MemberController {

    private final MemberService memberService;

        @PostMapping
        @Operation(summary = "회원가입", description = "회원가입 API")
    public ApiResponse<MemberResponseDto> createMember(@Valid @RequestBody MemberCreateRequestDto requestDto) {
        MemberResponseDto responseDto = memberService.createMember(requestDto);
            return onSuccess("회원가입에 성공했습니다.", responseDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "회원조회", description = "회원 단건 조회 API입니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "요청에 성공하였습니다.", content = @Content(mediaType = "application/json")),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "4002", description = "이미 가입된 계정입니다.", content = @Content(mediaType = "application/json")),
    })
    public ApiResponse<MemberResponseDto> getMember(@PathVariable Long id) {
        MemberResponseDto responseDto = memberService.getMember(id);
        return onSuccess("회원 조회에 성공했습니다.", responseDto);
    }
}
