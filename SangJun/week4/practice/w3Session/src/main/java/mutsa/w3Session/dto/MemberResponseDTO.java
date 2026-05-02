package mutsa.w3Session.dto;

import lombok.Getter;
import mutsa.w3Session.api.Member;

@Getter
public class MemberResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String pw;

    public MemberResponseDTO(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.email = member.getEmail();
        this.pw = member.getPw();
    }
}
