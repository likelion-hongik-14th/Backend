package musta_week5.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "ORDERS")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private Integer totalPrice;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void updateStatus(String status){
        this.status = status;
    }


}
