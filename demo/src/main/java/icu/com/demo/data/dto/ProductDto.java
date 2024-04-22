package icu.com.demo.data.dto;

import icu.com.demo.data.domain.Product;
import icu.com.demo.data.domain.Recipe;
import icu.com.demo.data.enums.ProductsCategories;
import lombok.Data;

import java.util.List;

@Data
public class ProductDto {
    private long id;
    private String name;
    private ProductsCategories category;
    private int calories;
    private List<Recipe> recipes;
}
