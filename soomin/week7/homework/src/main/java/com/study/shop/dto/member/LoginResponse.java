package com.study.shop.dto.member;

import com.study.shop.domain.Member;
import lombok.Getter;

@Getter
public class LoginResponse {

    private boolean success;
    private String message;
    private Long memberId;
    private String email;
    private String name;
    private String role;

    public LoginResponse(Member member) {
        this.success = true;
        this.message = "로그인 성공";
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.role = member.getRole();
    }

}
