package mutsa.w5homework.service;

import mutsa.w5homework.domain.Cart;
import mutsa.w5homework.domain.Member;
import mutsa.w5homework.dto.MemberCreateRequestDto;
import mutsa.w5homework.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import mutsa.w5homework.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public MemberResponseDto createMember(MemberCreateRequestDto dto) {
        Member member = Member.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
        Cart cart = new Cart(member);
        member.createCart(cart);
        Member savedMember = memberRepository.save(member);
        return new MemberResponseDto(savedMember);
    }

    @Transactional(readOnly = true)
    public MemberResponseDto getMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        return new MemberResponseDto(member);
    }
}
