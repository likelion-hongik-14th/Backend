package mutsa.week2.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "팔로우")
public class Follow {
    @Id
    @Column(name = "팔로우아이디")
    private Long id;

    @Column(name = "팔로우수")
    private Long followCount;

    @Column(name = "팔로잉아이디", nullable = false)
    private Long followingId;

    @Column(name = "팔로워아이디", nullable = false)
    private Long followerId;

    @Builder
    public Follow(Long id, Long followCount, Long followingId, Long followerId) {
        this.id = id;
        this.followCount = followCount;
        this.followingId = followingId;
        this.followerId = followerId;
    }
}