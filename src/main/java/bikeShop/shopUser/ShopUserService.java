package bikeShop.shopUser;

import bikeShop.ShopUserValidator;
import bikeShop.shopUser.shopUserModels.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShopUserService {

    private final ShopUserRepository shopUserRepository;
    private final JwtService jwtService;
    private final ShopUserValidator shopUserValidator;


    @Autowired
    public ShopUserService(ShopUserRepository shopUserRepository,
                           JwtService jwtService,
                           ShopUserValidator shopUserValidator) {
        this.shopUserRepository = shopUserRepository;
        this.jwtService = jwtService;
        this.shopUserValidator = shopUserValidator;
    }

    public List<ShopUserView> getAllShopUsers(ShopUserAuth shopUserAuth) {
        if (!this.shopUserValidator.shopAdminIsOk(shopUserAuth)) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access denied!");
        List<ShopUser> shopUsers = shopUserRepository.findAll();
        if (shopUsers.isEmpty()){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND,"No ShopUsers Found!");
        }
        ArrayList<ShopUserView> shopUserViews = new ArrayList<ShopUserView>();
        for (ShopUser shopUser: shopUsers) {
            if (shopUser.getShopUserId() != 1L) {
                ShopUserView shopUserView = new ShopUserView(
                        shopUser.getShopUserId(),
                        shopUser.getShopUserEmail(),
                        shopUser.getShopUserRole(),
                        shopUser.getYearOfBirth(),
                        shopUser.getMonthOfBirth(),
                        shopUser.getDayOfBirth(),
                        shopUser.getFirstName(),
                        shopUser.getMiddleName(),
                        shopUser.getLastName(),
                        shopUser.getStreet(),
                        shopUser.getHouseNr(),
                        shopUser.getPostalCode(),
                        shopUser.getCity());
                shopUserViews.add(shopUserView);
            }
        }
        return shopUserViews;
    }

    public ShopUserView getShopUserViewById(ShopUserAuth shopUserAuth,Long shopUserId) {
        ShopUser shopUser = getShopUserById(shopUserAuth,shopUserId);
        ShopUserView shopUserView = new ShopUserView(
                shopUser.getShopUserId(), shopUser.getShopUserEmail(),
                shopUser.getShopUserRole(),
                shopUser.getYearOfBirth(), shopUser.getMonthOfBirth(), shopUser.getDayOfBirth(),
                shopUser.getFirstName(), shopUser.getMiddleName(), shopUser.getLastName(),
                shopUser.getStreet(), shopUser.getHouseNr(),
                shopUser.getPostalCode(), shopUser.getCity());
        return shopUserView;
    }
    public ShopUser getShopUserById(ShopUserAuth shopUserAuth,Long shopUserId) {
        if (!(this.shopUserValidator.shopAdminIsOk(shopUserAuth) ||
                shopUserId.equals(getShopUserByEMail(shopUserAuth, shopUserAuth.getShopUserEmail()).getShopUserId()))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied!");
        }
        Optional<ShopUser> shopUserById = shopUserRepository.findShopUserByShopUserId(shopUserId);
        if (shopUserById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "ShopUser with id:  " + shopUserId + " does not exist!");
        }
        return shopUserById.get();
    }

    public ShopUserView getShopUserViewByEMail(ShopUserAuth shopUserAuth,String shopUserEmail) {

        ShopUser shopUser = getShopUserByEMail(shopUserAuth,shopUserEmail);
        ShopUserView shopUserView = new ShopUserView(
                shopUser.getShopUserId(),
                shopUser.getShopUserEmail(),
                shopUser.getShopUserRole(),
                shopUser.getYearOfBirth(),
                shopUser.getMonthOfBirth(),
                shopUser.getDayOfBirth(),
                shopUser.getFirstName(),
                shopUser.getMiddleName(),
                shopUser.getLastName(),
                shopUser.getStreet(),
                shopUser.getHouseNr(),
                shopUser.getPostalCode(),
                shopUser.getCity());
        return shopUserView;
    }
    public ShopUser getShopUserByEMail(ShopUserAuth shopUserAuth,String shopUserEmail) {
        // check user logs in the default guest auth is passed
        if (!this.shopUserValidator.shopAllUsersIsOk(shopUserAuth))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied!");
        Optional<ShopUser> shopUserByEmail = shopUserRepository.findShopUserByShopUserEmail(shopUserEmail);
        if (shopUserByEmail.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ShopUser with email: " + shopUserEmail + " does not exist!");
        }
        return shopUserByEmail.get();
    }

    public ShopUserAuth verifyLoginShopUser(LoginRequest loginRequest) {
        String wrongUserOrPassMsg = "Invalid combination of email and password!";
        // check on default guest email
        if (loginRequest.getEmail().equals("Z3Vlc3RAZWJpa25sLm5s")){
            Optional<ShopUser> shopUserOptional = shopUserRepository.findShopUserByShopUserEmail("Z3Vlc3RAZWJpa25sLm5s");
            if (shopUserOptional.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Start up failed!");
            }
            ShopUser shopUser = shopUserOptional.get();
            //in case the guest user does not exist throw exception
            ShopUserAuth shopUserAuth = new ShopUserAuth(jwtService.generateToken(shopUser.getShopUserEmail()), shopUser.getShopUserEmail());
            return shopUserAuth;
        }
        // the auth with jwt and role is checked
        if (!this.shopUserValidator.shopAllUsersIsOk(loginRequest.getCheckShopUserAuth()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied!");

        // get the shopuser with the email in the login request
        Optional<ShopUser> shopUserByEmail =
                Optional.ofNullable(getShopUserByEMail(loginRequest.getCheckShopUserAuth(), loginRequest.getEmail()));
        if (shopUserByEmail.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, wrongUserOrPassMsg);
        }

        // check if the provided password is correct for the user
        if (!shopUserByEmail.get().passwordCorrect(loginRequest.getPassword())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, wrongUserOrPassMsg);
        }

        // make the auth for the shopUser
        ShopUser user = shopUserByEmail.get();
        ShopUserAuth shopUserAuth = new ShopUserAuth(jwtService.generateToken(user.getShopUserEmail()), user.getShopUserEmail());
        return shopUserAuth;
    }

    public ShopUserAuth addNewShopUser(RegisterRequest registerRequest) {
        if (!this.shopUserValidator.shopAllUsersIsOk(registerRequest.getCheckShopUserAuth()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access denied!");

        ShopUser shopUser = registerRequest.getShopUser();

        Optional<ShopUser> shopUserByEmail = shopUserRepository.findShopUserByShopUserEmail(shopUser.getShopUserEmail());

        if (!shopUserByEmail.isEmpty()){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "ShopUser with email: " + shopUser.getShopUserEmail() +  " already exists!");
        }
        shopUser.setSaltedPassword();

        shopUser = shopUserRepository.save(shopUser);
        ShopUserAuth shopUserAuth = new ShopUserAuth(jwtService.generateToken(shopUser.getShopUserEmail()), shopUser.getShopUserEmail());

        return shopUserAuth;
    }

    @Transactional
    public ShopUser updateUserById(ShopUserAuth shopUserAuth,Long shopUserId, String newStreet,
                                   String newHouseNr, String newPostalCode,
                                   String newCity) {
        // a shopuser may change his own data, but admin may change data of all users
        if (shopUserId.equals(getShopUserByEMail(shopUserAuth, shopUserAuth.getShopUserEmail()).getShopUserId())
                ||
            this.shopUserValidator.shopAdminIsOk(shopUserAuth)) {

            Optional<ShopUser> shopUserById = shopUserRepository.findShopUserByShopUserId(shopUserId);

            if (!shopUserById.isEmpty()) {
                if ((newStreet != null) && (newStreet.length() > 0) && (!shopUserById.get().getStreet().equals(newStreet))) {
                    shopUserById.get().setStreet(newStreet);
                }
                if ((newHouseNr != null) && (newHouseNr.length() > 0) && (!shopUserById.get().getHouseNr().equals(newHouseNr))) {
                    shopUserById.get().setHouseNr(newHouseNr);
                }
                if ((newPostalCode != null) && (newPostalCode.length() > 0) && (!shopUserById.get().getPostalCode().equals(newPostalCode))) {
                    shopUserById.get().setPostalCode(newPostalCode);
                }
                if ((newCity != null) && (newCity.length() > 0) && (!shopUserById.get().getCity().equals(newCity))) {
                    shopUserById.get().setCity(newCity);
                }
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "ShopUser with id: " + shopUserId + " does not exist!");
            }
            return shopUserById.get();
        }
        else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access denied!");
        }
    }

    public void deleteShopUserById(ShopUserAuth shopUserAuth,Long shopUserId) {
        if (!this.shopUserValidator.shopAdminIsOk(shopUserAuth))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Access denied!");
        Optional<ShopUser> shopUserById = shopUserRepository.findShopUserByShopUserId(shopUserId);
        if (!shopUserById.isEmpty()){
            shopUserRepository.deleteById(shopUserId);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "ShopUser with id: " + shopUserId +  " does not exist!");
        }
    }

    public boolean shopUserByIdExists(long shopUserId) {
        Optional<ShopUser> shopUserById = shopUserRepository.findShopUserByShopUserId(shopUserId);
        if (shopUserById.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "ShopUser with id:  " + shopUserId + " does not exist!");
        }
        return true;
    }

}
