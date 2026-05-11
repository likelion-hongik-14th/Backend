package mutsa.w5homework.dto;

import lombok.Getter;

@Getter
public class MemberCreateRequestDto {
    private String name;
    private String email;
    private String password;
}
