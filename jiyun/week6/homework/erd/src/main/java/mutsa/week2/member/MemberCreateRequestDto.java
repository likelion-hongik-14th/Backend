package mutsa.week2.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberCreateRequestDto {

    @NotBlank(message = "name은 필수입니다.")
    private String name;
}
