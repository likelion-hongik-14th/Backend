package mutsa.week2.member;

import lombok.RequiredArgsConstructor;
import mutsa.week2.global.apiPayload.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponseDto create(MemberCreateRequestDto requestDto) {
        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new BusinessException(MemberErrorCode.MEMBER_ALREADY_EXISTS);
        }
        Member member = Member.builder()
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .build();
        return MemberResponseDto.from(memberRepository.save(member));
    }

    public MemberResponseDto findById(Long memberId) {
        return MemberResponseDto.from(getMember(memberId));
    }

    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}
