package mutsa.hw5.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Entity // JPA에게 이 클래스가 DB 테이블과 연동된 엔터티라는 걸 명시
@Getter // get 메서드를 자동 생성 (ex.getName)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 파라미터 없는 기본 생성자를 Protected로 자동 생성
// Protected로 하는 이유는 외부에서 막 생성하지 못 하게 막기 위해서
public class Member extends BaseEntity {

    @Id // PK를 JPA에게 알려줌
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // PK값 자동 생성, 대리키(키 값 자체로는 의미가 없는 거) 생성 때 사용
    private Long memberId;

    @Column(nullable = false)
    private String memberName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private Long memberAge;

    public static Member create(String memberName, String email, Long memberAge) {
    // 정적 책토리 메서드
    // protected Member(...)와 동일한 기능이지만, create라는 이름을 통해 의도가 명확해짐
    // static을 통해 객체를 만들고 호출할 필요 없이 바로 호출

        Member member = new Member();
        member.memberName = memberName;
        member.email = email;
        member.memberAge = memberAge;
        return member;
    }
}