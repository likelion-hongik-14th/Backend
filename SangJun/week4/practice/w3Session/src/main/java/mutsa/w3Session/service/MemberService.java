package mutsa.w3Session.service;

import lombok.RequiredArgsConstructor;
import mutsa.w3Session.entity.Member;
import mutsa.w3Session.dto.MemberCreateRequestDto;
import mutsa.w3Session.dto.MemberResponseDto;
import mutsa.w3Session.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponseDto createMember(MemberCreateRequestDto requestDTO){
        Member member = Member.builder()
                .name(requestDTO.getName())
                .email(requestDTO.getEmail())
                .pw(requestDTO.getPw())
                .build();
        Member savedMember = memberRepository.save(member);
        return new MemberResponseDto(savedMember);
    }

    @Transactional(readOnly = true)
    public MemberResponseDto getMember(Long id){
        Member member = memberRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다. " + id));
        return new MemberResponseDto(member);
    }
}
