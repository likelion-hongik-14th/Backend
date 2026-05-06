package mutsa.week2.follow;

import jakarta.persistence.*;
import lombok.*;
import mutsa.week2.member.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "팔로우")
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "팔로우아이디")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "팔로잉아이디", nullable = false)
    private Member following;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "팔로워아이디", nullable = false)
    private Member follower;

    @Builder
    public Follow(Member following, Member follower) {
        this.following = following;
        this.follower = follower;
    }
}
