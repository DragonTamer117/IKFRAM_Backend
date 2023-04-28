package application.models.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(String name) {
        Category category = categoryRepository.findByName(name).orElse(
                Category.builder()
                        .name(name)
                        .build()
        );

        categoryRepository.save(category);

        return category;
    }
}
