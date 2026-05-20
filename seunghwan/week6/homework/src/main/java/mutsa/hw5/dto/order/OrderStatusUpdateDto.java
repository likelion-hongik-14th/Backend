package mutsa.hw5.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import mutsa.hw5.domain.OrderStatus;

@Getter
public class OrderStatusUpdateDto {

    @NotNull
    private OrderStatus status;
}
