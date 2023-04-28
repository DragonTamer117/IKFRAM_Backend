package application.models.storage_products;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StorageProductRepository extends JpaRepository<StorageProduct, UUID> {
    Optional<StorageProduct> findByName(String name);
}
