package mutsa.homework.domain;

public enum OrderStatus {
    ORDERED("주문 완료"),
    SHIPPING("배송중"),
    DELIVERED("배송 완료"),
    CANCELED("주문 취소");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
