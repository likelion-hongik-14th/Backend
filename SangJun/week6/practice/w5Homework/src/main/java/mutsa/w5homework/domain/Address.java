package mutsa.w5homework.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String addressName;
    private String zipCode;
    private String cityAddress;
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Address(String addressName, String zipCode, String cityAddress, String phoneNumber, Member member) {
        this.addressName = addressName;
        this.zipCode = zipCode;
        this.cityAddress = cityAddress;
        this.phoneNumber = phoneNumber;
        this.member = member;
    }

    public void update(String addressName, String zipCode, String cityAddress, String phoneNumber) {
        if (addressName != null && !addressName.trim().isEmpty()) {
            this.addressName = addressName;
        }
        if (zipCode != null && !zipCode.trim().isEmpty()) {
            this.zipCode = zipCode;
        }
        if (cityAddress != null && !cityAddress.trim().isEmpty()) {
            this.cityAddress = cityAddress;
        }
        if (phoneNumber != null && !phoneNumber.trim().isEmpty()) {
            this.phoneNumber = phoneNumber;
        }
    }
}
