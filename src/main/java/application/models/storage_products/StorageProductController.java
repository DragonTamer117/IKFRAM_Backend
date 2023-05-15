package application.models.storage_products;

import application.dtos.StorageProductDTO;
import application.models.users.UserService;
import com.zhaofujun.automapper.AutoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/storageProducts")
public class StorageProductController {
    private final AutoMapper mapper = new AutoMapper();
    private final StorageProductService storageProductService;
    private final UserService userService;

    @GetMapping
    public List<StorageProduct> findAll() {
        return storageProductService.findAll().stream()
                .map(storageProduct -> mapper.map(storageProduct, StorageProduct.class))
                .collect(toList());
    }

    @PostMapping
    public ResponseEntity<StorageProduct> create(
            @RequestHeader("Authorization") String bearerToken,
            @RequestBody StorageProductDTO storageProductDTO
    ) {
        if (userService.isAllowedRole(bearerToken)) {
            StorageProduct storageProduct = storageProductService.create(storageProductDTO);
            return ResponseEntity.ok(storageProduct);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
