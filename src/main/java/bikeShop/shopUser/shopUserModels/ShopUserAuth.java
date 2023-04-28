package bikeShop.shopUser.shopUserModels;

public class ShopUserAuth {
    private final String jwt;
    private String shopUserEmail;

    public ShopUserAuth(String jwt, String shopUserEmail) {
        this.jwt = jwt;
        this.shopUserEmail = shopUserEmail;
    }

    public String getJwt() {
        return jwt;
    }

    public String getShopUserEmail() {
        return shopUserEmail;
    }

    @Override
    public String toString() {
        return "ShopUserAuth{" +
                "jwt='" + jwt + '\'' +
                ", shopUserEmail =" + shopUserEmail +
                '}';
    }
}
