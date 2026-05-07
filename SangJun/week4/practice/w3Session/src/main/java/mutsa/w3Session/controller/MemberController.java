package mutsa.w3Session.controller;


import lombok.RequiredArgsConstructor;
import mutsa.w3Session.dto.MemberCreateRequestDto;
import mutsa.w3Session.dto.MemberResponseDto;
import mutsa.w3Session.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<MemberResponseDto> createMember(@RequestBody MemberCreateRequestDto requestDTO){
        MemberResponseDto responseDTO = memberService.createMember(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> getMember(@PathVariable Long memberId){
        MemberResponseDto responseDTO = memberService.getMember(memberId);
        return ResponseEntity.ok(responseDTO);
    }


}
