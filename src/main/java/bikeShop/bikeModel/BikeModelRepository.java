package bikeShop.bikeModel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BikeModelRepository extends JpaRepository<BikeModel, Long> {
    Optional<BikeModel> findBikeModelByBikeModelId(Long bikeModelId);
}
