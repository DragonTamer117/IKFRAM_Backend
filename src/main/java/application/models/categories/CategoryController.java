package application.models.categories;

import application.dtos.CategoryDTO;
import com.zhaofujun.automapper.AutoMapper;
import lombok.RequiredArgsConstructor;
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

    @CrossOrigin
    @GetMapping("/{id}")
    public CategoryDTO find(@PathVariable("id") UUID id) {
        Category category = categoryService.findById(id);
        return mapper.map(category, CategoryDTO.class);
    }

    @CrossOrigin
    @PostMapping
    public Category create(@RequestHeader("Authorization") String bearerToken, @RequestBody CategoryDTO categoryDTO) {
        return categoryService.createCategory(categoryDTO.getName());
    }

    @CrossOrigin
    @GetMapping("/findAll")
    public List<CategoryDTO> findAllCategories() {
        return categoryService.findAllCategories().stream()
                .map(category -> mapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }
}
