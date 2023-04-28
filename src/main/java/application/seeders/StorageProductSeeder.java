package application.seeders;

import application.models.categories.Category;
import application.models.categories.CategoryService;
import application.models.storage_products.StorageProduct;
import application.models.storage_products.StorageProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class StorageProductSeeder implements CommandLineRunner{

    private final StorageProductRepository storageProductsRepository;
    private final CategoryService categoryService;

    private final List<List<Object>> storageProductsList = List.of(
            List.of("Sport bike", "Vet koele bike!", 1900.00, 10, "http://167.86.87.154/bike_photos/bike01.webp"),
            List.of("Speed bike", "Super Electro Bike!", 4100.00, 10, "http://167.86.87.154/bike_photos/bike02.webp"),
            List.of("Speed bike", "Snelle Sport bike!", 2100.00, 10, "http://167.86.87.154/bike_photos/bike03.webp"),
            List.of("Sport bike", "Blauwe Sport Bike!", 3300.00, 10, "http://167.86.87.154/bike_photos/bike04.webp"),
            List.of("Normal bike", "Zwarte Elektrische Dames Fiets!", 2900.00, 10, "http://167.86.87.154/bike_photos/bike05.webp"),
            List.of("Normal bike", "Grijze Elektrische Dames Fiets!", 3100.00, 10, "http://167.86.87.154/bike_photos/bike06.webp"),
            List.of("Normal bike", "Witte Elektrische Dames Fiets!", 2900.00, 10, "http://167.86.87.154/bike_photos/bike07.webp"),
            List.of("Normal bike", "Roze Elektrische Dames Fiets!", 5200.00, 10, "http://167.86.87.154/bike_photos/bike08.webp"),
            List.of("Normal bike", "Zwart-Grijze Elektrische Fiets!", 3400.00, 10, "http://167.86.87.154/bike_photos/bike09.webp"),
            List.of("Range bike", "Long-Range Dames Fiets!", 3100.00, 10, "http://167.86.87.154/bike_photos/bike10.webp"),
            List.of("Range bike", "Long-Range in-stap Fiets!", 1750.00, 10, "http://167.86.87.154/bike_photos/bike11.webp"),
            List.of("Mountain bike", "Normale Heren Fiets!", 900.00, 10, "http://167.86.87.154/bike_photos/bike12.webp"),
            List.of("Sport bike", "Stoere Mannelijke Electro Bike!", 3500.00, 10, "http://167.86.87.154/bike_photos/bike13.webp"),
            List.of("Utility bike", "Electrische Bak Fiets!", 2100.00, 10, "http://167.86.87.154/bike_photos/bike14.webp"),
            List.of("Sport bike", "Stoere Vrouwelijke Electro Bike!", 2950.00, 10, "http://167.86.87.154/bike_photos/bike15.webp")
    );

    private void createStorageProductsAndCategory() {
        for (List<Object> storageProducts : storageProductsList) {
            String categoryName = (String) storageProducts.get(0);
            Category category = categoryService.createCategory(categoryName);

            String name = (String) storageProducts.get(1);
            Double price = (Double) storageProducts.get(2);
            String imageUrl = (String) storageProducts.get(4);

            StorageProduct storageProduct = StorageProduct.builder()
                    .name(name)
                    .price(price)
                    .inStock(true)
                    .imageUrl(imageUrl)
                    .category(category)
                    .build();

            Optional<StorageProduct> existingStorageProduct = storageProductsRepository.findByName(storageProduct.getName());

            if (existingStorageProduct.isEmpty()) {
                storageProductsRepository.save(storageProduct);
            }
        }
    }

    @Override
    public void run(String ...args) {
        createStorageProductsAndCategory();
    }
}
