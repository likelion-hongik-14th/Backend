package mutsa.session5.Dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberResponseDto {
    private Long memberId;
    private String email;
    private String phoneNumber;
}
