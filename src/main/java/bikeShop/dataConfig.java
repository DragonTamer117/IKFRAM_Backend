package bikeShop;

import bikeShop.bike.Bike;
import bikeShop.bike.BikeRepository;
import bikeShop.bikeModel.BikeModel;
import bikeShop.bikeModel.BikeModelRepository;
import bikeShop.bikeOrder.BikeOrder;
import bikeShop.bikeOrder.BikeOrderRepository;
import bikeShop.bikeOrder.BikeOrderStatus;
import bikeShop.shopUser.JwtService;
import bikeShop.shopUser.ShopUserService;
import bikeShop.shopUser.shopUserModels.ShopUser;
import bikeShop.shopUser.ShopUserRepository;
import bikeShop.shopUser.shopUserModels.ShopUserAuth;
import bikeShop.shopUser.shopUserModels.ShopUserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Base64;

@Configuration
public class dataConfig {

    @Bean
    CommandLineRunner commandLineRunner(ShopUserRepository shopUserRepository,
                                        BikeModelRepository bikeModelRepository,
                                        BikeRepository bikeRepository,
                                        BikeOrderRepository bikeOrderRepository,
                                        ShopUserService shopUserService,
                                        JwtService jwtService) {
        return args -> {
            BikeModel bikeModel1 = new BikeModel(
                "Vet koele bike!",
                1900.00F,
                10,
                "http://167.86.87.154/bike_photos/bike01.webp"
            );
            bikeModelRepository.save(bikeModel1);

            BikeModel bikeModel2 = new BikeModel(
                    "Super Electro Bike!",
                    4100.00F,
                    10,
                    "http://167.86.87.154/bike_photos/bike02.webp"
            );
            bikeModel2 = bikeModelRepository.save(bikeModel2);

            BikeModel bikeModel3 = new BikeModel(
                    "Snelle Sport bike!",
                    2100.00F,
                    10,
                    "http://167.86.87.154/bike_photos/bike03.webp"
            );
            bikeModel3 = bikeModelRepository.save(bikeModel3);

            BikeModel bikeModel4 = new BikeModel(
                    "Blauwe Sport Bike!",
                    3300.00F,
                    10,
                    "http://167.86.87.154/bike_photos/bike04.webp"
            );
            bikeModel4 = bikeModelRepository.save(bikeModel4);

            BikeModel bikeModel5 = new BikeModel(
                    "Zwarte Elektrische Dames Fiets!",
                    2900.00F,
                    10,
                    "http://167.86.87.154/bike_photos/bike05.webp"
            );
            bikeModel5 = bikeModelRepository.save(bikeModel5);

            BikeModel bikeModel6 = new BikeModel(
                    "Grijze Elektrische Dames Fiets!",
                    3100.00F,
                    10,
                    "http://167.86.87.154/bike_photos/bike06.webp"
            );
            bikeModel6 = bikeModelRepository.save(bikeModel6);

            BikeModel bikeModel7 = new BikeModel(
                    "Witte Elektrische Dames Fiets!",
                    2900.00F,
                    10,
                    "http://167.86.87.154/bike_photos/bike07.webp"
            );
            bikeModel7 = bikeModelRepository.save(bikeModel7);

            BikeModel bikeModel8 = new BikeModel(
                    "Roze Elektrische Dames Fiets!",
                    5200.00F,
                    10,
                    "http://167.86.87.154/bike_photos/bike08.webp"
            );
            bikeModel8 = bikeModelRepository.save(bikeModel8);

            BikeModel bikeModel9 = new BikeModel(
                    "Zwart-Grijze Elektrische Fiets!",
                    3400.00F,
                    10,
                    "http://167.86.87.154/bike_photos/bike09.webp"
            );
            bikeModel9 = bikeModelRepository.save(bikeModel9);

            BikeModel bikeModel10 = new BikeModel(
                    "Long-Range Dames Fiets!",
                    3100.00F,
                    10,
                    "http://167.86.87.154/bike_photos/bike10.webp"
            );
            bikeModel10 = bikeModelRepository.save(bikeModel10);

            BikeModel bikeModel11 = new BikeModel(
                    "Long-Range in-stap Fiets!",
                    1750.00F,
                    10,
                    "http://167.86.87.154/bike_photos/bike11.webp"
            );
            bikeModel11 = bikeModelRepository.save(bikeModel11);

            BikeModel bikeModel12 = new BikeModel(
                    "Normale Heren Fiets!",
                    900.00F,
                    10,
                    "http://167.86.87.154/bike_photos/bike12.webp"
            );
            bikeModel12 =  bikeModelRepository.save(bikeModel12);

            BikeModel bikeModel13 = new BikeModel(
                    "Stoere Mannelijke Electro Bike!",
                    3500.00F,
                    10,
                    "http://167.86.87.154/bike_photos/bike13.webp"
            );
            bikeModel13 = bikeModelRepository.save(bikeModel13);

            BikeModel bikeModel14 = new BikeModel(
                    "Electrische Bak Fiets!",
                    2100.00F,
                    10,
                    "http://167.86.87.154/bike_photos/bike14.webp"
            );
            bikeModel14 = bikeModelRepository.save(bikeModel14);

            BikeModel bikeModel15 = new BikeModel(
                    "Stoere Vrouwelijke Electro Bike!",
                    2950.00F,
                    10,
                    "http://167.86.87.154/bike_photos/bike15.webp"
            );
            bikeModel15 = bikeModelRepository.save(bikeModel15);

            //========================================================================
            String guestEmail = "guest@ebiknl.nl";
            String guestEmailEncode = Base64.getEncoder().encodeToString(guestEmail.getBytes());
            String guestPass = "123456";
            String guestPassEncode = Base64.getEncoder().encodeToString(guestPass.getBytes());
            ShopUser guestUser = new ShopUser (
                    guestEmailEncode,
                    guestPassEncode,
                    ShopUserRole.GUEST,
                    1990,
                    1,
                    1,
                    "Gast",
                    "",
                    "",
                    "",
                    "",
                    "123456",
                    ""
            );
            guestUser = shopUserRepository.save(guestUser);
            ShopUserAuth guestUserAuth = new ShopUserAuth(jwtService.generateToken(guestEmailEncode),guestEmailEncode);

            //========================================================================
            String sysEmail = "SysAdmin@users.com";
            String sysEmailEncode = Base64.getEncoder().encodeToString(sysEmail.getBytes());
            String sysPass = "sysadmin";
            String sysPassEncode = Base64.getEncoder().encodeToString(sysPass.getBytes());
            ShopUser SYS_Admin = new ShopUser (
                    sysEmailEncode,
                    sysPassEncode,
                    ShopUserRole.SYS_ADMIN,
                    1950,
                    10,
                    16,
                    "Sys",
                    "",
                    "Admin",
                    "Longway ",
                    "1000",
                    "1111ZZ",
                    "FarAwayTown"
            );
            SYS_Admin.setSaltedPassword();
            SYS_Admin = shopUserRepository.save(SYS_Admin);
            ShopUserAuth SYS_AdminAuth = new ShopUserAuth(jwtService.generateToken(sysEmailEncode),sysEmailEncode);

            String dataEmail = "DataAdmin@users.com";
            String dataEmailEncode = Base64.getEncoder().encodeToString(dataEmail.getBytes());
            String dataPass = "dataAdmin";
            String dataPassEncode = Base64.getEncoder().encodeToString(dataPass.getBytes());
            ShopUser Data_Admin = new ShopUser (
                    dataEmailEncode,
                    dataPassEncode,
                    ShopUserRole.DATA_ADMIN,
                    1900,
                    10,
                    16,
                    "Data",
                    "",
                    "Boss",
                    "Shortway",
                    "1",
                    "3333CC",
                    "NearyTown"
            );
            Data_Admin.setSaltedPassword();
            Data_Admin = shopUserRepository.save(Data_Admin);
            ShopUserAuth Data_AdminAuth = new ShopUserAuth(jwtService.generateToken(dataEmailEncode),dataEmailEncode);

            String cusEmail = "customer@users.com";
            String cusEmailEncode = Base64.getEncoder().encodeToString(cusEmail.getBytes());
            String cusPass = "customer";
            String cusPassEncode = Base64.getEncoder().encodeToString(cusPass.getBytes());
            ShopUser customer = new ShopUser (
                    cusEmailEncode,
                    cusPassEncode,
                    ShopUserRole.CUSTOMER,
                    1900,
                    10,
                    16,
                    "Customer",
                    "",
                    "User",
                    "MiddelWay",
                    "100",
                    "2222AA",
                    "AnyTown"
            );
            customer.setSaltedPassword();
            customer = shopUserRepository.save(customer);

            BikeOrder bikeOrder1 = new BikeOrder(BikeOrderStatus.ORDER_CONFIRMED, customer.getShopUserId());
            bikeOrder1 = bikeOrderRepository.save(bikeOrder1);

            BikeOrder bikeOrder2 = new BikeOrder(BikeOrderStatus.ORDER_CONFIRMED, Data_Admin.getShopUserId());
            bikeOrder2 = bikeOrderRepository.save(bikeOrder2);

            Bike newBike = new Bike(bikeModel1.getBikeModelName(), bikeModel1,
                            bikeModel1.getPriceOfTheDay(), bikeOrder1,
                            false,true);
            bikeRepository.save(newBike);
            newBike = new Bike(bikeModel2.getBikeModelName(), bikeModel2,
                    bikeModel2.getPriceOfTheDay(), bikeOrder1,
                    false,true);
            bikeRepository.save(newBike);
            newBike = new Bike(bikeModel3.getBikeModelName(), bikeModel3,
                    bikeModel3.getPriceOfTheDay(), bikeOrder1,
                    false,true);
            bikeRepository.save(newBike);
            newBike = new Bike(bikeModel4.getBikeModelName(), bikeModel4,
                    bikeModel4.getPriceOfTheDay(), bikeOrder2,
                    false,true);
            bikeRepository.save(newBike);
            newBike = new Bike(bikeModel5.getBikeModelName(), bikeModel5,
                    bikeModel5.getPriceOfTheDay(), bikeOrder2,
                    false,true);
            bikeRepository.save(newBike);
        };
    }
}
