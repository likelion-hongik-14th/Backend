package com.study.shop.service;

import com.study.shop.domain.Cart;
import com.study.shop.domain.Member;
import com.study.shop.dto.member.LoginRequest;
import com.study.shop.dto.member.LoginResponse;
import com.study.shop.dto.member.MemberResponse;
import com.study.shop.dto.member.SignupRequest;
import com.study.shop.repository.CartRepository;
import com.study.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;

    @Transactional
    public MemberResponse signup(SignupRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        Member member = new Member(
                request.getEmail(),
                request.getPassword(),
                request.getName()
        );

        Member savedMember = memberRepository.save(member);

        Cart cart = new Cart(savedMember);
        cartRepository.save(cart);

        return new MemberResponse(savedMember);
    }

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다."));

        if (!member.getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        return new LoginResponse(member);
    }

    @Transactional(readOnly = true)
    public MemberResponse getMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id=" + memberId));

        return new MemberResponse(member);
    }
}
