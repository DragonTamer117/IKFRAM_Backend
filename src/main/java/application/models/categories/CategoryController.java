package application.models.categories;

import application.dtos.CategoryDTO;
import application.models.users.UserService;
import com.zhaofujun.automapper.AutoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final AutoMapper mapper = new AutoMapper();
    private final CategoryService categoryService;
    private final UserService userService;

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> find(@RequestHeader("Authorization") String bearerToken, @PathVariable("id") UUID id) {
        Category category = categoryService.findById(id);

        if (userService.isAllowedRole(bearerToken)) {
            return ResponseEntity.ok(mapper.map(category, CategoryDTO.class));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Category> create(@RequestHeader("Authorization") String bearerToken, @RequestBody CategoryDTO categoryDTO) {
        if (userService.isAllowedRole(bearerToken)) {
            return ResponseEntity.ok(categoryService.createCategory(categoryDTO.getName()));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @CrossOrigin
    @GetMapping("/findAll")
    @CrossOrigin(origins = "http://localhost:7700")
    public ResponseEntity<List<CategoryDTO>> findAllCategories(@RequestHeader("Authorization") String bearerToken) {
        System.out.println("CategoryDTO: " + bearerToken);
        if (userService.isAllowedRole(bearerToken)) {
            return ResponseEntity.ok(categoryService.findAllCategories().stream()
                    .map(category -> mapper.map(category, CategoryDTO.class))
                    .collect(Collectors.toList()));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
