package mutsa.api.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")//현재 테이블에 생성될 FK 칼럼 이름 지정
    //JoinColumn 안써도 JPA가 자동으로 만들어주긴 하나, 가독성을 위해 작성함
    private Users users;

    private String name;

    @Column(name = "zip_code")
    private String zipCode;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    public static Address create(Users users, String name, String zipCode, String address, String phoneNumber) {
        return Address.builder()
                .users(users)
                .name(name)
                .zipCode(zipCode)
                .address(address)
                .phoneNumber(phoneNumber)
                .build();
    }

    public void update(String name, String zipCode, String address, String phoneNumber) {
        this.name = name;
        this.zipCode = zipCode;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
