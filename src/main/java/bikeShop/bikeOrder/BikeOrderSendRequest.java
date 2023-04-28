package bikeShop.bikeOrder;

import bikeShop.shopUser.shopUserModels.ShopUserAuth;

import java.util.ArrayList;

public class BikeOrderSendRequest {
    public ArrayList<Long> bikeModelIds;
    public ShopUserAuth shopUserAuth;

    public BikeOrderSendRequest(ArrayList<Long> bikeModelIds, ShopUserAuth shopUserAuth) {
        this.bikeModelIds = bikeModelIds;
        this.shopUserAuth = shopUserAuth;
    }
}
