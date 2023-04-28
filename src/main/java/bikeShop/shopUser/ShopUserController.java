package bikeShop.shopUser;

import bikeShop.shopUser.shopUserModels.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/shopUser")
public class ShopUserController {

    private final ShopUserService shopUserService;

    @Autowired
    public ShopUserController(ShopUserService shopUserService) {
        this.shopUserService = shopUserService;
    }

    @PostMapping
    public List<ShopUserView> getAllShopUsers(@RequestBody ShopUserAuth shopUserAuth){
        return shopUserService.getAllShopUsers(shopUserAuth);
    }

    @PostMapping(path = "/id/{shopUserId}")
    public ShopUserView getShopUserById(@RequestBody ShopUserAuth shopUserAuth, @PathVariable("shopUserId") Long shopUserID){
        ShopUserView shopUserWithId = shopUserService.getShopUserViewById(shopUserAuth, shopUserID);
        return shopUserWithId;
    }

    @PostMapping(path = "/mail/{shopUserEmail}")
    public ShopUserView getShopUserByEMail(@RequestBody ShopUserAuth shopUserAuth,@PathVariable("shopUserEmail") String eMail){
        ShopUserView shopUserWithEMail = shopUserService.getShopUserViewByEMail(shopUserAuth,eMail);
        return shopUserWithEMail;
    }

    @PostMapping(path = "/login")
    public ShopUserAuth LoginShopUser(@RequestBody LoginRequest loginRequest){
        return shopUserService.verifyLoginShopUser(loginRequest);
    }

    @PostMapping("/register")
    public ShopUserAuth registerNewShopUser(@RequestBody RegisterRequest registerRequest) {
        ShopUserAuth newUser = shopUserService.addNewShopUser(registerRequest);
        return newUser;
    }


    @PostMapping(path = "/delete/{shopUserId}")
    public void deleteShopUserById(@RequestBody ShopUserAuth shopUserAuth,@PathVariable("shopUserId") Long shopUserId) {
        shopUserService.deleteShopUserById(shopUserAuth,shopUserId);
    }

    @PostMapping(path = "/update/{shopUserId}")
    public ShopUser updateShopUserAddressById(@RequestBody ShopUserAuth shopUserAuth,
            @PathVariable("shopUserId") Long shopUserId,
            @RequestParam(required = false) String newStreet,
            @RequestParam(required = false) String newHouseNr,
            @RequestParam(required = false) String newPostalCode,
            @RequestParam(required = false) String newCity){
        return shopUserService.updateUserById(shopUserAuth,shopUserId,newStreet,newHouseNr,newPostalCode,newCity);
    }

}
