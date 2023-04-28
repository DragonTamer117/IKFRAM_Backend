package bikeShop.bike;

import bikeShop.bikeOrder.BikeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Long> {
    List<Bike> findAllByBikeOrder(BikeOrder bikeOrder);
}