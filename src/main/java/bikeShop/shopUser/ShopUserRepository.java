package bikeShop.shopUser;

import bikeShop.shopUser.shopUserModels.ShopUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopUserRepository extends JpaRepository<ShopUser, Long> {

    Optional<ShopUser> findShopUserByShopUserId(Long shopUserId);
    Optional<ShopUser> findShopUserByShopUserEmail(String shopUserEmail);
}
