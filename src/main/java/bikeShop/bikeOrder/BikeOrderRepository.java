package bikeShop.bikeOrder;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BikeOrderRepository extends JpaRepository<BikeOrder, Long> {
    Optional<BikeOrder> findBikeOrderByOrderId(Long bikeOrderId);
}
