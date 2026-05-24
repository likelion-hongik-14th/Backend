package mutsa.session5.Entity;

import jakarta.persistence.*;
import lombok.*;
import mutsa.session5.Dto.MemberResponseDto;

@Entity
@Getter
@Table(name = "member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    private String password;
    private String phoneNumber;
    private String email;
}
