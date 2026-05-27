package mutsa.session5.Controller;

import lombok.RequiredArgsConstructor;
import mutsa.session5.Dto.MemberRequestDto;
import mutsa.session5.Dto.MemberResponseDto;
import mutsa.session5.Service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponseDto> createMember(@RequestBody MemberRequestDto requestDto) {
        // 서비스에서 저장 후 ResponseDto로 변환해서 반환하도록 구현
        return ResponseEntity.ok(memberService.saveMember(requestDto));
    }
}
