package application.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatus {
    ORDER_NOT_CONFIRMED(0),
    ORDER_CONFIRMED(1),
    ORDER_BEING_PROCESSED(2),
    ORDER_COMPLETED(3),
    ORDER_SHIPPED(4),
    ORDER_DELIVERED(5),
    ORDER_CANCELLED(6);

    private final int value;

    public int getValue() {
        return value;
    }
}
