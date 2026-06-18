package mutsa.week2.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberCreateRequestDto {

    @NotBlank(message = "name은 필수입니다.")
    private String name;

    @NotBlank(message = "email은 필수입니다.")
    @Email(message = "올바른 이메일 형식이어야 합니다.")
    private String email;
}
