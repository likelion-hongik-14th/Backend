package mutsa.week2.address;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mutsa.week2.member.Member;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "배송지")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "배송지아이디")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "회원아이디", nullable = false)
    private Member member;

    @Column(name = "배송지명", nullable = false, length = 30)
    private String name;

    @Column(name = "우편번호", nullable = false, length = 10)
    private String zipCode;

    @Column(name = "주소", nullable = false, length = 200)
    private String address;

    @Column(name = "전화번호", nullable = false, length = 20)
    private String phone;

    @Builder
    public Address(Member member, String name, String zipCode, String address, String phone) {
        this.member = member;
        this.name = name;
        this.zipCode = zipCode;
        this.address = address;
        this.phone = phone;
    }

    public void update(String name, String zipCode, String address, String phone) {
        this.name = name;
        this.zipCode = zipCode;
        this.address = address;
        this.phone = phone;
    }
}
