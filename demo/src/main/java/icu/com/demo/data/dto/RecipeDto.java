package icu.com.demo.data.dto;

import icu.com.demo.data.domain.Product;
import icu.com.demo.data.enums.CategoriesOfDishes;
import lombok.Data;

import java.util.List;

@Data
public class RecipeDto {
    private long id;
    private String name;
    private String description;
    private CategoriesOfDishes category;
    private int calories;
    private List<Product> products;
}
