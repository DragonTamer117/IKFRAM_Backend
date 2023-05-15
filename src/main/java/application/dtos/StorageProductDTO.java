package application.dtos;

import application.models.categories.Category;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

import javax.persistence.JoinColumn;

@Data
public class StorageProductDTO {
    private String name;
    @JoinColumn(name = "category_name")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "name")
    private Category category;
    private double price;
    private boolean inStock;
    private boolean isSoldOut;
    private String imageUrl;
}
