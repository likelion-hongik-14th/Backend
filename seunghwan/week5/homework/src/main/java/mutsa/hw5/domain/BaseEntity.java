package mutsa.hw5.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter // get 메서드를 자동 생성 (ex.getName)
@MappedSuperclass // JPA에게 이걸 엔터티로 만들지 말고 그냥 상속용으로 쓴다고 표시
@EntityListeners(AuditingEntityListener.class)
// 엔터티에 저장 or 수정이 발생할 때 AuditingEntityListener가 자동으로 시간을 감지해서 넣어줌

public class BaseEntity {

    @CreatedDate // 엔터티가 처음 저장될 때 현재 시간을 자동으로 넣어줌
    private LocalDateTime createdAt;

    @LastModifiedDate // 엔터티가 수정될 때 현재 시간을 자동으로 넣어줌
    private LocalDateTime updatedAt;
}