package mutsa.session5.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    private String addressName;
    private String zipCode;
    private String baseAddress;
    private String detailAddress;
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateAddress(String addressName, String zipCode, String baseAddress, String detailAddress, String phoneNumber) {
        this.addressName = addressName;
        this.zipCode = zipCode;
        this.baseAddress = baseAddress;
        this.detailAddress = detailAddress;
        this.phoneNumber = phoneNumber;
    }
}
