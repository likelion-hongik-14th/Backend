package mutsa.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignupRequestDto {
    private String email;
    private String password;
    private String name;
}
