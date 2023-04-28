package bikeShop.shopUser.shopUserModels;

public class RegisterRequest {
    private ShopUser shopUser;
    private ShopUserAuth shopUserAuth;

    public RegisterRequest(ShopUser shopUser, ShopUserAuth shopUserAuth) {
        this.shopUser = shopUser;
        this.shopUserAuth = shopUserAuth;
    }

    public ShopUser getShopUser() {
        return shopUser;
    }

    public ShopUserAuth getCheckShopUserAuth() {
        return shopUserAuth;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "shopUser=" + shopUser +
                ", shopUserAuth=" + shopUserAuth +
                '}';
    }
}
