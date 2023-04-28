package bikeShop;

import static org.hamcrest.Matchers.hasSize;

import bikeShop.bikeOrder.BikeOrderSendRequest;
import bikeShop.bikeOrder.BikeOrderService;
import bikeShop.shopUser.*;
import bikeShop.shopUser.shopUserModels.ShopUser;
import bikeShop.shopUser.shopUserModels.ShopUserAuth;
import bikeShop.shopUser.shopUserModels.ShopUserRole;
import bikeShop.shopUser.shopUserModels.ShopUserView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Base64;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestBikeOrderController extends TestConfig {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BikeOrderService bikeOrderService;

    @Autowired
    private ShopUserRepository shopUserRepository;

    @Autowired
    ShopUserService shopUserService;

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
    public void t101_ShouldReturnOKWhenYouAddABikeOrdersForSameUserAPI() {
        String addABikeOrderEmail = "addABikeOrder@users.com";
        String addABikeOrderEmailEncode = Base64.getEncoder().encodeToString(addABikeOrderEmail.getBytes());
        String addABikeOrderPass = "delete";
        String addABikeOrderPassEncode = Base64.getEncoder().encodeToString(addABikeOrderPass.getBytes());

        ShopUser user1 = new ShopUser (
                addABikeOrderEmailEncode,
                addABikeOrderPassEncode,
                ShopUserRole.CUSTOMER,
                2000,
                10,
                16,
                "deleteKlant",
                "",
                "Appel",
                "Saffraanweg",
                "8",
                "2215WB",
                "Voorhout"
        );
        user1 = shopUserRepository.save(user1);

        ArrayList<Long> bikeModelIds = new ArrayList<Long>();
        bikeModelIds.add(3L);
        bikeModelIds.add(4L);

        ShopUserAuth shopUserAuth = new ShopUserAuth(jwtService.generateToken(addABikeOrderEmailEncode), addABikeOrderEmailEncode);
        BikeOrderSendRequest bikeOrderSendRequest = new BikeOrderSendRequest(bikeModelIds,shopUserAuth);

        try {
            mvc.perform(MockMvcRequestBuilders
                            .post("/bikeorder/"+user1.getShopUserId())
                            .content(asJsonString(bikeOrderSendRequest))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t111_ShouldOKWhenGetAllBikeOrders(){
        ShopUser user1 = new ShopUser (
                "user1_t111@users.com",
                "delete",
                ShopUserRole.CUSTOMER,
                2000,
                10,
                16,
                "deleteKlant",
                "",
                "Appel",
                "Saffraanweg",
                "8",
                "2215WB",
                "Voorhout"
        );
        ShopUser persShopUser1 = shopUserRepository.save(user1);
        Long shopUserIdFirst = persShopUser1.getShopUserId();

        ArrayList<Long> bikeModelIds = new ArrayList<Long>();
        bikeModelIds.add(3L);
        bikeModelIds.add(4L);

        ShopUserAuth shopUserAuth = new ShopUserAuth(jwtService.generateToken(persShopUser1.getShopUserEmail()), persShopUser1.getShopUserEmail());
        BikeOrderSendRequest bikeOrderSendRequest = new BikeOrderSendRequest(bikeModelIds,shopUserAuth);

        Long bikeOrderIdFirst = bikeOrderService.addNewBikeOrder(bikeOrderSendRequest, shopUserIdFirst);
        System.out.println(bikeOrderIdFirst);

        ShopUser user2 = new ShopUser (
                "user2_t111@users@users.com",
                "delete",
                ShopUserRole.DATA_ADMIN,
                2000,
                10,
                16,
                "deleteKlant",
                "",
                "Appel",
                "Saffraanweg",
                "8",
                "2215WB",
                "Voorhout"
        );
        bikeModelIds = new ArrayList<Long>();
        bikeModelIds.add(5L);
        bikeModelIds.add(6L);


        ShopUser persShopUser2 = shopUserRepository.save(user2);
        Long shopUserIdSecond = persShopUser2.getShopUserId();

        shopUserAuth = new ShopUserAuth(jwtService.generateToken(persShopUser2.getShopUserEmail()), persShopUser2.getShopUserEmail());
        bikeOrderSendRequest = new BikeOrderSendRequest(bikeModelIds,shopUserAuth);

        Long bikeOrderIdSecond = bikeOrderService.addNewBikeOrder(bikeOrderSendRequest, shopUserIdSecond);
        System.out.println(bikeOrderIdSecond);


        try {
            mvc.perform(MockMvcRequestBuilders
                            .post("/bikeorder")
                            .content(asJsonString(shopUserAuth))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$",hasSize(6)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t112_ShouldNotBeOKWhenGetAllBikeOrdersByCustomer(){


        ShopUser user2 = new ShopUser (
                "user2_t112@user2_t112.com",
                "user2_t112user2_t112",
                ShopUserRole.CUSTOMER,
                2000,
                10,
                16,
                "deleteKlant",
                "",
                "Appel",
                "Saffraanweg",
                "8",
                "2215WB",
                "Voorhout"
        );

        ShopUser persShopUser2 = shopUserRepository.save(user2);

        ShopUserAuth shopUserAuth = new ShopUserAuth(jwtService.generateToken(persShopUser2.getShopUserEmail()), persShopUser2.getShopUserEmail());

        try {
            mvc.perform(MockMvcRequestBuilders
                            .post("/bikeorder")
                            .content(asJsonString(shopUserAuth))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isForbidden());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
