package mutsa.w3Session.controller;


import lombok.RequiredArgsConstructor;
import mutsa.w3Session.dto.MemberCreateRequestDTO;
import mutsa.w3Session.dto.MemberResponseDTO;
import mutsa.w3Session.service.MemberServie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberServie memberServie;

    @PostMapping
    public ResponseEntity<MemberResponseDTO> createMember(@RequestBody MemberCreateRequestDTO requestDTO){
        MemberResponseDTO responseDTO = memberServie.createMember(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponseDTO> getMember(@PathVariable Long memberId){
        MemberResponseDTO responseDTO = memberServie.getMember(memberId);
        return ResponseEntity.ok(responseDTO);
    }


}
