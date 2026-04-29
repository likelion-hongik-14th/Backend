package mutsa.w3Session.dto;

import lombok.Getter;

@Getter
public class MemberCreateRequestDTO {
    private String name;
    private String email;
    private String pw;
}
