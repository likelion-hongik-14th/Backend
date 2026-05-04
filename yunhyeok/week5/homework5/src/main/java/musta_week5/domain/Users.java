package musta_week5.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = " USERS")
@Getter
@NoArgsConstructor

public class Users {

    @Id
    private String userId;

    private String email;

    private String password;

    private String name;

    private LocalDateTime createdAt;

}
