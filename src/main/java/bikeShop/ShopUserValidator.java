package bikeShop;

import bikeShop.shopUser.JwtService;
import bikeShop.shopUser.ShopUserRepository;
import bikeShop.shopUser.ShopUserService;
import bikeShop.shopUser.shopUserModels.ShopUser;
import bikeShop.shopUser.shopUserModels.ShopUserAuth;
import bikeShop.shopUser.shopUserModels.ShopUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ShopUserValidator {

    private final ShopUserRepository shopUserRepository;
    private final JwtService jwtService;

    @Autowired
    public ShopUserValidator(ShopUserRepository shopUserRepository, JwtService jwtService) {
        this.shopUserRepository = shopUserRepository;
        this.jwtService = jwtService;
    }

    private final List<ShopUserRole> allUsers = List.of(ShopUserRole.GUEST,ShopUserRole.SYS_ADMIN, ShopUserRole.DATA_ADMIN, ShopUserRole.CUSTOMER);
    private final List<ShopUserRole> customers = List.of(ShopUserRole.SYS_ADMIN, ShopUserRole.DATA_ADMIN, ShopUserRole.CUSTOMER);
    private final List<ShopUserRole> admins = List.of(ShopUserRole.SYS_ADMIN, ShopUserRole.DATA_ADMIN);

    public ShopUserRole getShopUserRole(ShopUserAuth shopUserAuth){

        Optional<ShopUser> shopUser =
                this.shopUserRepository.findShopUserByShopUserEmail(shopUserAuth.getShopUserEmail());

        if (shopUser.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ShopUser does not exist!");

        return shopUser.get().getShopUserRole();
    }

    public boolean checkJwt(ShopUserAuth shopUserAuth){
        if (!this.jwtService.validateToken(shopUserAuth.getJwt(), shopUserAuth.getShopUserEmail())) return false;

        return true;
    }

    public boolean shopAllUsersIsOk(ShopUserAuth shopUserAuth){
        this.checkJwt(shopUserAuth);

        ShopUserRole shopUserRole = getShopUserRole(shopUserAuth);

        if (!this.allUsers.contains(shopUserRole)) return false;

        return true;
    }

    public boolean shopCustomerIsOK(ShopUserAuth shopUserAuth){
        this.checkJwt(shopUserAuth);

        ShopUserRole shopUserRole = getShopUserRole(shopUserAuth);

        if (!this.customers.contains(shopUserRole)) return false;

        return true;
    }

    public boolean shopAdminIsOk(ShopUserAuth shopUserAuth){
        this.checkJwt(shopUserAuth);

        ShopUserRole shopUserRole = getShopUserRole(shopUserAuth);

        if (!this.admins.contains(shopUserRole)) return false;

        return true;
    }

}
