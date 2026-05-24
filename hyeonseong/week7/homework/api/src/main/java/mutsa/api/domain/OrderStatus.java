package mutsa.api.domain;

public enum OrderStatus {
    ORDERED, // 주문 완료
    PAID, // 결제 완료
    DELIVERED, // 배송 완료
    CANCELED // 취소
}
