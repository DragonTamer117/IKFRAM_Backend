package bikeShop;

import bikeShop.shopUser.JwtService;
import bikeShop.shopUser.ShopUserRepository;
import bikeShop.shopUser.shopUserModels.ShopUserAuth;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Base64;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestBikeModelController extends TestConfig {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ShopUserRepository shopUserRepository;

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
    public void t001_ShouldReturnOkWhenYougetAllBikeModelsAPI() {
        String guestEmail = "guest@ebiknl.nl";
        String guestEmailEncode = Base64.getEncoder().encodeToString(guestEmail.getBytes());
        ShopUserAuth shopUserAuth = new ShopUserAuth(jwtService.generateToken(guestEmailEncode),guestEmailEncode);
        try {
            mvc.perform(MockMvcRequestBuilders
                            .post("/bikeModel")
                            .content(asJsonString(shopUserAuth))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$",hasSize(15)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
