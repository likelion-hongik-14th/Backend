package mutsa.hw5.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// get 메서드를 자동 생성 (ex.getName)
@Getter
// JPA에게 이걸 엔터티로 만들지 말고 그냥 상속용으로 쓴다고 표시
@MappedSuperclass
// 엔터티에 저장 or 수정이 발생할 때 AuditingEntityListener가 자동으로 시간을 감지해서 넣어줌
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    // 엔터티가 처음 저장될 때 현재 시간을 자동으로 넣어줌
    @CreatedDate
    private LocalDateTime createdAt;

    // 엔터티가 수정될 때 현재 시간을 자동으로 넣어줌
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
