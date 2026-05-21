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
    private String zip_code;
    private String address;
    private String phone_number;

    public static Address create(Users users, String name, String zip_code, String address, String phone_number) {
        return Address.builder()
                .users(users)
                .name(name)
                .zip_code(zip_code)
                .address(address)
                .phone_number(phone_number)
                .build();
    }

    public void update(String name, String zip_code, String address, String phone_number) {
        this.name = name;
        this.zip_code = zip_code;
        this.address = address;
        this.phone_number = phone_number;
    }
}
