package mutsa.session2.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter

public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private Member follower; // 팔로우를 하는 사람 (나)

    @ManyToOne
    @JoinColumn(name = "following_id")
    private Member following; // 팔로우를 당하는 사람 (상대방)
}