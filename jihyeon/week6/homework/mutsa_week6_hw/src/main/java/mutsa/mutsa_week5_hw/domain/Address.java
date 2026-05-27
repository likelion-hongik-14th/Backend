package mutsa.mutsa_week5_hw.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String zipcode;
    private String address;
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void update(String name, String zipcode, String address, String phone) {
        this.name = name;
        this.zipcode = zipcode;
        this.address = address;
        this.phone = phone;
    }
}
