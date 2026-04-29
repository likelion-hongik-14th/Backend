package mutsa.week2.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "회원")
public class Member {
    @Id
    @Column(name = "회원아이디")
    private Long id;

    @Column(name = "이름")
    private String name;

    @Column(name = "비밀번호", length = 50)
    private String password;

    @Column(name = "이메일", length = 50)
    private String email;

    @Column(name = "성별", length = 1)
    private String gender;

    @Column(name = "가입일")
    private LocalDate joinDate;

    @Builder
    public Member(Long id, String name, String password, String email, String gender, LocalDate joinDate) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.gender = gender;
        this.joinDate = joinDate;
    }

    public void updateProfile(String name, String email) {
        this.name = name;
        this.email = email;
    }
}