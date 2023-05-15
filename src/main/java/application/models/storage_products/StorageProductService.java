package application.models.storage_products;

import application.dtos.StorageProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StorageProductService {
    private final StorageProductRepository storageProductRepository;

    public Optional<StorageProduct> findById(UUID id) {
        return storageProductRepository.findById(id);
    }

    public List<StorageProduct> findAll() {
        return storageProductRepository.findAll();
    }

    public StorageProduct create(StorageProductDTO storageProductDTO) {
        StorageProduct storageProduct = StorageProduct.builder()
                .name(storageProductDTO.getName())
                .price(storageProductDTO.getPrice())
                .inStock(true)
                .imageUrl(storageProductDTO.getImageUrl())
                .category(storageProductDTO.getCategory())
                .build();

        storageProductRepository.save(storageProduct);

        return storageProduct;
    }
}
