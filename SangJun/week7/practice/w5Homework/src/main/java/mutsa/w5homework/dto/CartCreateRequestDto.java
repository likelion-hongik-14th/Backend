package mutsa.w5homework.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CartCreateRequestDto {
    @NotNull
    private Long memberId;
}
