package mutsa.mutsa_week5_hw.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 주문 생성(요청) -> 장바구니에 담긴 상품 일괄 주문 시, 회원 ID와 배송지 ID를 서버로 전달
@Getter
@NoArgsConstructor
public class OrderFromCartRequestDto {

    @NotNull(message = "배송지 ID는 필수 입력사항입니다.")
    private Long addressId;

}
