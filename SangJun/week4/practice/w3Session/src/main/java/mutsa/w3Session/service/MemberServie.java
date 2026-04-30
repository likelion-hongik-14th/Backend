package mutsa.w3Session.service;

import lombok.RequiredArgsConstructor;
import mutsa.w3Session.api.Member;
import mutsa.w3Session.dto.MemberCreateRequestDTO;
import mutsa.w3Session.dto.MemberResponseDTO;
import mutsa.w3Session.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServie {
    private final MemberRepository memberRepository;
    //기사 작성 로직
    public MemberResponseDTO createMember(MemberCreateRequestDTO requestDTO){
        Member member = new Member (requestDTO.getName(), requestDTO.getEmail(), requestDTO.getPw());
        Member savedMember = memberRepository.save(member);
        return new MemberResponseDTO(savedMember);
    }

    public MemberResponseDTO getMember(Long id){
        Member member = memberRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("해당 멤버가 없습니다. " + id));
        return new MemberResponseDTO(member);
    }
}
