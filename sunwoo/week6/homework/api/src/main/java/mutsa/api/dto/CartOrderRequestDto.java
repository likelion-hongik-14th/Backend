package mutsa.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CartOrderRequestDto {
    @NotNull(message = "Address id must not be null")
    private Long addressId;
}
