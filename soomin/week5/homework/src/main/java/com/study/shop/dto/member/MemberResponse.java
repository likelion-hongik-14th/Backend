package com.study.shop.dto.member;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.study.shop.domain.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonPropertyOrder({"memberId", "email", "name", "role", "createdAt"})
public class MemberResponse {

    private Long memberId;
    private String email;
    private String name;
    private String role;
    private LocalDateTime createdAt;

    public MemberResponse(Member member) {
        this.memberId = member.getId();
        this.email = member.getEmail();
        this.name = member.getName();
        this.role = member.getRole();
        this.createdAt = member.getCreatedAt();
    }

}
