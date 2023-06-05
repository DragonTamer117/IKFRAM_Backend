package application.models.products;

import application.models.orders.Order;
import application.models.storage_products.StorageProduct;
import application.models.storage_products.StorageProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final StorageProductService storageProductService;

    public void create(UUID id, Order order) {
        StorageProduct foundProduct = storageProductService.findById(id).get();

        Product product = Product.builder()
                .name(foundProduct.getName())
                .price(foundProduct.getPrice())
                .category(foundProduct.getCategory())
                .imageUrl(foundProduct.getImageUrl())
                .order(order)
                .build();

        productRepository.save(product);
    }
}
