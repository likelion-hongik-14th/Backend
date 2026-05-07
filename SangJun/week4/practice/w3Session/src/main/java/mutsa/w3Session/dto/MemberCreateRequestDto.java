package mutsa.w3Session.dto;

import lombok.Getter;

@Getter
public class MemberCreateRequestDto {
    private String name;
    private String email;
    private String pw;
}
