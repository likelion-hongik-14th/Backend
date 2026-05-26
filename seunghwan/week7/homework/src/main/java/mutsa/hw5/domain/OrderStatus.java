package mutsa.hw5.domain;

public enum OrderStatus {
    ORDER_COMPLETED,   // 주문완료
    PAYMENT_COMPLETED, // 결제완료
    CANCELLED,         // 취소
    DELIVERED;         // 배송완료

    // 현재 상태에서 next 상태로 전이 가능한지 검증
    public boolean canTransitionTo(OrderStatus next) {
        return switch (this) {
            case ORDER_COMPLETED   -> next == PAYMENT_COMPLETED || next == CANCELLED;
            case PAYMENT_COMPLETED -> next == DELIVERED || next == CANCELLED;
            case DELIVERED, CANCELLED -> false;
        };
    }
}
