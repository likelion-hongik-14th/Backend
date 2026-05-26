package mutsa.session5.Controller;

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
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ApiResponse<MemberResponseDto> createMember(@RequestBody MemberRequestDto requestDto) {
        MemberResponseDto response = memberService.saveMember(requestDto);
        return ApiResponse.onSuccess(MemberSuccessCode.CREATE_MEMBER_SUCCESS.getMessage(), response);
    }
}
