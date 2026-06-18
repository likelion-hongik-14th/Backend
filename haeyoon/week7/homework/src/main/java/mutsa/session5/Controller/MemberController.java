package mutsa.session5.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mutsa.session5.Dto.MemberRequestDto;
import mutsa.session5.Dto.MemberResponseDto;
import mutsa.session5.Service.MemberService;
import mutsa.session5.global.apipayload.ApiResponse;
import mutsa.session5.global.apipayload.exception.code.MemberSuccessCode;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Tag(name = "회원(Member) API", description = "회원 가입 및 회원 관리 API")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    @Operation(summary = "일반 회원가입", description = "새로운 회원을 등록할 때 사용하는 API")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "회원가입이 완료되었습니다. (MEMBER_201)",
                    content = @Content(mediaType = "application/json")
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "이미 존재하는 이메일입니다. (MEMBER_400_1)",
                    content = @Content(mediaType = "application/json")
            )
    })
    public ApiResponse<MemberResponseDto> createMember(@RequestBody MemberRequestDto requestDto) {
        MemberResponseDto response = memberService.saveMember(requestDto);
        return ApiResponse.onSuccess(MemberSuccessCode.CREATE_MEMBER_SUCCESS.getMessage(), response);
    }
}
