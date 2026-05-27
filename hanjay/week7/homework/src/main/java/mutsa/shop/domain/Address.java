package mutsa.shop.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private String name;
    private String postalcode;
    private String address;
    private String phone;

    // Address.java (엔티티 클래스) 내부에 추가
    public void update(String name, String postalcode, String address, String phone) {
        this.name = name;
        this.postalcode = postalcode;
        this.address = address;
        this.phone = phone;
    }
}
