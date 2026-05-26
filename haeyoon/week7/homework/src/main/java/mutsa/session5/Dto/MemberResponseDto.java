package mutsa.session5.Dto;

import lombok.*;
import mutsa.session5.Entity.Member;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberResponseDto {
    private Long memberId;
    private String email;
    private String phoneNumber;

    public static MemberResponseDto from(Member member) {
        return MemberResponseDto.builder()
                .memberId(member.getMemberId())
                .phoneNumber(member.getPhoneNumber())
                .email(member.getEmail())
                .build();
    }
}
