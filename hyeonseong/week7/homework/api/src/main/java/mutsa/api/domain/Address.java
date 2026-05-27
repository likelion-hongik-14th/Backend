package mutsa.api.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 회원 탈퇴시 배송지도 날아가야 하므로 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String addressName; // 배송지명(집이나 회사 등)

    @Column(nullable = false)
    private String zipcode; // 우편번호

    @Column(nullable = false)
    private String address; // 상세 주소

    @Column(nullable = false)
    private String phoneNumber; // 전화번호

    @Builder
    public Address(User user, String addressName, String zipcode, String address, String phoneNumber) {
        this.user = user;
        this.addressName = addressName;
        this.zipcode = zipcode;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // 도메인 비즈니스 로직: 배송지 정보 수정
    public void updateAddress(String addressName, String zipcode, String address, String phoneNumber){
        this.addressName = addressName;
        this.zipcode = zipcode;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
