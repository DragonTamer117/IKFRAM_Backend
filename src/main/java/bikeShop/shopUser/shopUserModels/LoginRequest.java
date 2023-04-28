package bikeShop.shopUser.shopUserModels;

public class LoginRequest {
    private String email;
    private String password;
    private ShopUserAuth checkShopUserAuth;


    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public ShopUserAuth getCheckShopUserAuth() {
        return checkShopUserAuth;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", checkShopUserAuth=" + checkShopUserAuth +
                '}';
    }
}
