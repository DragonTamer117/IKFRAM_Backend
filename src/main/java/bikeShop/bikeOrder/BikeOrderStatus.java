package bikeShop.bikeOrder;

public enum BikeOrderStatus {
    ORDER_NOT_CONFIRMED(0),
    ORDER_CONFIRMED(1),
    ORDER_BEING_PROCESSED(2),
    ORDER_COMPLETED(3),
    ORDER_SHIPPED(4),
    ORDER_DELIVERED(5),
    ORDER_CANCELED(6);

    private final int value;

    BikeOrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
