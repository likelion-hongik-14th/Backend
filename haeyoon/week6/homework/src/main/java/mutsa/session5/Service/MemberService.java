package mutsa.session5.Service;

import lombok.RequiredArgsConstructor;
import mutsa.session5.Dto.MemberRequestDto;
import mutsa.session5.Dto.MemberResponseDto;
import mutsa.session5.Entity.Member;
import mutsa.session5.Repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponseDto saveMember(MemberRequestDto requestDto) {
        Member member = Member.builder()
                .password(requestDto.getPassword())
                .phoneNumber(requestDto.getPhoneNumber())
                .email(requestDto.getEmail())
                .build();

        Member savedMember = memberRepository.save(member);

        return MemberResponseDto.builder()
                .memberId(savedMember.getMemberId())
                .email(savedMember.getEmail())
                .phoneNumber(savedMember.getPhoneNumber())
                .build();
    }
}
