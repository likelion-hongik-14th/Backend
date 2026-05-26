package mutsa.week2.member;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mutsa.week2.global.apiPayload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member", description = "회원 API")
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "회원 등록", description = "신규 회원을 등록합니다. 이미 가입된 이메일이면 실패합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_2001", description = "회원 등록 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_4001", description = "요청 형식이 올바르지 않습니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER_4091", description = "이미 가입된 이메일입니다.")
    })
    public ApiResponse<MemberResponseDto> create(
            @Valid @RequestBody MemberCreateRequestDto requestDto) {
        return ApiResponse.onSuccess("회원 등록 성공", memberService.create(requestDto));
    }

    @GetMapping("/{memberId}")
    @Operation(summary = "회원 조회", description = "memberId로 회원 단건을 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON_2000", description = "회원 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER_4041", description = "해당 회원은 존재하지 않습니다.")
    })
    public ApiResponse<MemberResponseDto> findById(@PathVariable Long memberId) {
        return ApiResponse.onSuccess("회원 조회 성공", memberService.findById(memberId));
    }
}
