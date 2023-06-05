package application.models.orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("SELECT o FROM Order o WHERE o.userId = :userId")
    List<Order> findAllByUserId(@Param("userId") String userId);

    @Override
    Optional<Order> findById(UUID uuid);
}
