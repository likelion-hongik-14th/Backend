package mutsa.homework.domain;

import jakarta.persistence.*;
import lombok.*;
import mutsa.homework.global.common.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String addressName;
    private String address;
    private String zipCode;
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder(access = AccessLevel.PRIVATE)
    private Address(
            User user,
            String addressName,
            String address,
            String zipCode,
            String phoneNumber
    ) {
        this.user = user;
        this.addressName = addressName;
        this.address = address;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
    }

    public static Address create(
            User user,
            String addressName,
            String address,
            String zipCode,
            String phoneNumber
    ){
        Address newAddress = Address.builder()
                .user(user)
                .addressName(addressName)
                .address(address)
                .zipCode(zipCode)
                .phoneNumber(phoneNumber)
                .build();

        user.addAddress(newAddress);

        return newAddress;
    }

    public void validateUser(Long userId){
        if (!this.user.getId().equals(userId)){
            throw new IllegalArgumentException("잘못된 접근입니다. (권한 없음)");
        }
    }

    public void updateAddress(
            String addressName,
            String address,
            String zipCode,
            String phoneNumber
    ){
        this.addressName = addressName;
        this.address = address;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
    }
}
