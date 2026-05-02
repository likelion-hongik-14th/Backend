package mutsa.w3Session.dto;

import lombok.Getter;
import mutsa.w3Session.entity.Member;

@Getter
public class MemberResponseDto {
    private Long id;
    private String name;
    private String email;

    public MemberResponseDto(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
    }
}
