package mutsa.mutsa_week5_hw.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 주문 생성(요청) -> 특정 상품 구매 시, 해당 상품의 ID 및 주문 수량, 회원 및 배송지 정보를 서버로 전달
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDirectRequestDto {

    @NotNull(message = "상품 ID는 필수 입력사항입니다.")
    private Long productId;

    @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
    private int quantity;

    @NotNull(message = "배송지 ID는 필수 입력사항입니다.")
    private Long addressId;

}
