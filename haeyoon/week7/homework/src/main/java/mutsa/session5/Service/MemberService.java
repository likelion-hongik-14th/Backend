package mutsa.session5.Service;

import mutsa.session5.Dto.MemberRequestDto;
import mutsa.session5.Dto.MemberResponseDto;
import mutsa.session5.Entity.Member;
import mutsa.session5.Repository.MemberRepository;
import mutsa.session5.global.apipayload.exception.MemberException;
import mutsa.session5.global.apipayload.exception.code.MemberErrorCode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public MemberResponseDto saveMember(MemberRequestDto requestDto) {

        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new MemberException(MemberErrorCode.DUPLICATE_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        Member member = Member.builder()
                .password(encodedPassword)
                .phoneNumber(requestDto.getPhoneNumber())
                .email(requestDto.getEmail())
                .build();

        Member savedMember = memberRepository.save(member);
        return MemberResponseDto.from(savedMember);
    }
}
