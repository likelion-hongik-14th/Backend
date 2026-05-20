package mutsa.hw5.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

// JPA에게 이 클래스가 DB 테이블과 연동된 엔터티라는 걸 명시
@Entity
// get 메서드를 자동 생성 (ex.getName)
@Getter
// 파라미터 없는 기본 생성자를 Protected로 자동 생성
// Protected로 하는 이유는 외부에서 막 생성하지 못 하게 막기 위해서
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address extends BaseEntity {

    // PK를 JPA에게 알려줌
    @Id
    // PK값 자동 생성, 대리키(키 값 자체로는 의미가 없는 거) 생성 때 사용
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;

    // Address N : Member 1을 의미
    // Address를 조회할 때 현재 이 객체를 당장 가져오는 것이 아닌 실제 사용 시 DB에서 가져 옴
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String addressName;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phoneNumber;

    // 정적 팩토리 메서드
    // protected Address(...)와 동일한 기능이지만, create라는 이름을 통해 의도가 명확해짐
    // static을 통해 객체를 만들고 호출할 필요 없이 바로 호출
    public static Address create(Member member, String addressName, String postalCode,
                                 String address, String phoneNumber) {
        Address addr = new Address();
        addr.member = member;
        addr.addressName = addressName;
        addr.postalCode = postalCode;
        addr.address = address;
        addr.phoneNumber = phoneNumber;
        return addr;
    }

    // 인스턴스 메서드 -> 이미 존재하는 객체에서 호출
    // PATCH로 메서드를 설정했기 때문에 일부만 수정하게 할려고
    // Setter를 쓰지 않음으로서 외부에서 값이 막 변경되는 것을 막고,
    // update라는 매서드를 씀으로서 직관적으로 업데이트 하는 것을 알 수 있음
    public void update(String addressName, String postalCode, String address, String phoneNumber) {
        if (addressName != null) this.addressName = addressName;
        if (postalCode != null) this.postalCode = postalCode;
        if (address != null) this.address = address;
        if (phoneNumber != null) this.phoneNumber = phoneNumber;
    }
}
