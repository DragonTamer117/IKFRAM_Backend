package bikeShop;

import bikeShop.shopUser.*;
import bikeShop.shopUser.shopUserModels.RegisterRequest;
import bikeShop.shopUser.shopUserModels.ShopUser;
import bikeShop.shopUser.shopUserModels.ShopUserAuth;
import bikeShop.shopUser.shopUserModels.ShopUserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Base64;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestShopUserController extends TestConfig {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ShopUserService shopUserService;
    @Autowired
    private ShopUserRepository shopUserRepository;
    @Autowired
    private JwtService jwtService;


    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void t001_ShouldReturnOkWhenYougetAllShopUserViewsAPI() {

        String sysEmail = "SysAdmin@users.com";
        String sysEmailEncode = Base64.getEncoder().encodeToString(sysEmail.getBytes());
        ShopUser shopUser = shopUserRepository.findShopUserByShopUserEmail(sysEmailEncode).get();
        ShopUserAuth shopUserAuth = new ShopUserAuth(jwtService.generateToken(shopUser.getShopUserEmail()),shopUser.getShopUserEmail());

        try {
            mvc.perform(MockMvcRequestBuilders
                            .post("/shopUser")
                            .content(asJsonString(shopUserAuth))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                            .andDo(print())
                            .andExpect(status().isOk())
                            .andExpect(jsonPath("$",hasSize(4)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t002_ShouldReturnOKWhenYouAddAShopUserAPI() {

        String guestEmail = "guest@ebiknl.nl";
        String guestEmailEncode = Base64.getEncoder().encodeToString(guestEmail.getBytes());
        String newUserEMail = "newUser@ebiknl.nl";
        String newUserEMailEncode = Base64.getEncoder().encodeToString(newUserEMail.getBytes());
        String newUserPass = "123456";
        String newUserPassEncode = Base64.getEncoder().encodeToString(newUserPass.getBytes());

        ShopUser newShopUser = new ShopUser (
                newUserEMailEncode,
                newUserPassEncode,
                ShopUserRole.CUSTOMER,
                2000,
                10,
                16,
                "newklant",
                "",
                "newklant",
                "Saffraanweg",
                "8",
                "2215WB",
                "Voorhout"
        );

        ShopUser shopUser = shopUserRepository.findShopUserByShopUserEmail(guestEmailEncode).get();
        ShopUserAuth shopUserAuth = new ShopUserAuth(jwtService.generateToken(shopUser.getShopUserEmail()),shopUser.getShopUserEmail());
        RegisterRequest registerRequest = new RegisterRequest(newShopUser,shopUserAuth);
        try {
            mvc.perform(MockMvcRequestBuilders
                            .post("/shopUser/register")
                            .content(asJsonString(registerRequest))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                            .andDo(print())
                            .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t004_ShouldReturnConflictWhenYouAddUserWithSameEmailAPI() {
        String guestEmail = "guest@ebiknl.nl";
        String guestEmailEncode = Base64.getEncoder().encodeToString(guestEmail.getBytes());
        String newUserEMail = "sameuserWithEmail@users.com";
        String newUserEMailEncode = Base64.getEncoder().encodeToString(newUserEMail.getBytes());
        String newUserPass = "123456";
        String newUserPassEncode = Base64.getEncoder().encodeToString(newUserPass.getBytes());

        ShopUser newShopUserWithSameEmail1 = new ShopUser (
                newUserEMailEncode,
                newUserPassEncode,
                ShopUserRole.CUSTOMER,
                2000,
                10,
                16,
                "newklant",
                "",
                "newklant",
                "Saffraanweg",
                "8",
                "2215WB",
                "Voorhout"
        );

        ShopUser guestShopUser = shopUserRepository.findShopUserByShopUserEmail(guestEmailEncode).get();
        ShopUserAuth shopUserAuth = new ShopUserAuth(jwtService.generateToken(guestShopUser.getShopUserEmail()),guestShopUser.getShopUserEmail());
        RegisterRequest registerRequest = new RegisterRequest(newShopUserWithSameEmail1,shopUserAuth);
        try {
            mvc.perform(MockMvcRequestBuilders
                    .post("/shopUser/register")
                    .content(asJsonString(registerRequest))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mvc.perform(MockMvcRequestBuilders
                            .post("/shopUser/register")
                            .content(asJsonString(registerRequest))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                            .andDo(print())
                            .andExpect(status().isConflict());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t011_GetShopUserWithIdAPI() {
        String adminEmail = "DataAdmin@users.com";
        String adminEmailEncode = Base64.getEncoder().encodeToString(adminEmail.getBytes());
        ShopUser adminShopUser = shopUserRepository.findShopUserByShopUserEmail(adminEmailEncode).get();
        ShopUserAuth shopUserAuth = new ShopUserAuth(jwtService.generateToken(adminShopUser.getShopUserEmail()),adminShopUser.getShopUserEmail());
        try {
            mvc.perform(MockMvcRequestBuilders
                            .post("/shopUser/id/1")
                            .content(asJsonString(shopUserAuth))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t012_GetShopUserWithEMailAPI() {
        String guestEmail = "guest@ebiknl.nl";
        String guestEmailEncode = Base64.getEncoder().encodeToString(guestEmail.getBytes());
        ShopUser guestShopUser = shopUserRepository.findShopUserByShopUserEmail(guestEmailEncode).get();
        ShopUserAuth shopUserAuth = new ShopUserAuth(jwtService.generateToken(guestShopUser.getShopUserEmail()),guestShopUser.getShopUserEmail());

        //this user user is created in the config/dataconfig.java
        try {
            mvc.perform(MockMvcRequestBuilders
                            .post("/shopUser/mail/"+guestEmailEncode)
                            .content(asJsonString(shopUserAuth))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t021_ShouldReturnNotFoundWhenGettingADeletedShopUserWithIdAPI() {
        String deleteEncodeEmail = "DeleteKlantWithID6L@newklant.com";
        String EmailEncode = Base64.getEncoder().encodeToString(deleteEncodeEmail.getBytes());
        ShopUser deleteShopUser = new ShopUser (
                    EmailEncode,
                    "DeleteKlantWithID",
                    ShopUserRole.DATA_ADMIN,
                    2000,
                    10,
                    16,
                    "newklant",
                    "",
                    "newklant",
                    "Saffraanweg",
                    "8",
                    "2215WB",
                    "Voorhout"
            );

        Long shopUserId = shopUserRepository.save(deleteShopUser).getShopUserId();
        String adminEmail = "DataAdmin@users.com";
        String adminEmailEncode = Base64.getEncoder().encodeToString(adminEmail.getBytes());
        ShopUser adminShopUser = shopUserRepository.findShopUserByShopUserEmail(adminEmailEncode).get();
        ShopUserAuth shopUserAuth = new ShopUserAuth(jwtService.generateToken(adminShopUser.getShopUserEmail()),adminShopUser.getShopUserEmail());
        try {
            mvc.perform(MockMvcRequestBuilders
                            .post("/shopUser/delete/"+shopUserId)
                            .content(asJsonString(shopUserAuth))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mvc.perform(MockMvcRequestBuilders
                            .post("/shopUser/id/"+shopUserId)
                            .content(asJsonString(shopUserAuth))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t031_UpdateShopUserWithIdAPI() {

        String guestEmail = "guest@ebiknl.nl";
        String guestEmailEncode = Base64.getEncoder().encodeToString(guestEmail.getBytes());

        ShopUser shopUser = shopUserRepository.findShopUserByShopUserEmail(guestEmailEncode).get();
        ShopUserAuth shopUserAuth = new ShopUserAuth(jwtService.generateToken(shopUser.getShopUserEmail()),shopUser.getShopUserEmail());

        String updateEmail = "UpdateKlantWithID6L@newklant.com";
        String updateEmailEncode = Base64.getEncoder().encodeToString(updateEmail.getBytes());
        String updatePass = "update";
        String updatePassEncode = Base64.getEncoder().encodeToString(updatePass.getBytes());
        ShopUser updateShopUser = new ShopUser (
                updateEmailEncode,
                updatePassEncode,
                ShopUserRole.CUSTOMER,
                2000,
                10,
                16,
                "updateShopUser",
                "updateShopUser",
                "updateShopUser",
                "Saffraanweg",
                "8",
                "2215WB",
                "Voorhout"
        );

        RegisterRequest registerRequest = new RegisterRequest(updateShopUser,shopUserAuth);
        try {
            mvc.perform(MockMvcRequestBuilders
                            .post("/shopUser/register")
                            .content(asJsonString(registerRequest))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
        updateShopUser = shopUserRepository.findShopUserByShopUserEmail(updateEmailEncode).get();
        Long shopUserId = updateShopUser.getShopUserId();
        shopUserAuth = new ShopUserAuth(jwtService.generateToken(updateShopUser.getShopUserEmail()),updateShopUser.getShopUserEmail());

        try {
            mvc.perform(MockMvcRequestBuilders
                            .post("/shopUser/update/"+shopUserId+"?newStreet=appeltaart&newHouseNr=9999&postalCode=&city=")
                            .content(asJsonString(shopUserAuth))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            mvc.perform(MockMvcRequestBuilders
                            .post("/shopUser/id/"+shopUserId)
                            .content(asJsonString(shopUserAuth))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}