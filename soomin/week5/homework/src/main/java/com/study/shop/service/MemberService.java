package com.study.shop.service;

import com.study.shop.domain.Cart;
import com.study.shop.domain.Member;
import com.study.shop.dto.MemberResponse;
import com.study.shop.dto.SignupRequest;
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
}
