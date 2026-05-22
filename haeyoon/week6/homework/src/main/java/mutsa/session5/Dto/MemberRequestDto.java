package mutsa.session5.Dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberRequestDto {
    private String password;
    private String phoneNumber;
    private String email;
}
