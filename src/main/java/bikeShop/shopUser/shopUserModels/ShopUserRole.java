package bikeShop.shopUser.shopUserModels;

public enum ShopUserRole {
    GUEST(0),
    CUSTOMER(1),
    SYS_ADMIN(2),
    DATA_ADMIN(3);

    private final int value;

    ShopUserRole(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
