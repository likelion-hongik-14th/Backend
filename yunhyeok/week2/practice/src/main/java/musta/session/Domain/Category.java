package musta.session.Domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor

public class Category {

    @Id
    @Column(name = "category")

    private String category;

    private String social;
    private String entertain;
    private String economy;
    private String science;
    private String overseas;


}
