package icu.com.demo.data.dto;

import icu.com.demo.data.domain.Product;
import icu.com.demo.data.domain.Recipe;
import icu.com.demo.data.enums.ProductsCategories;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class CreateProductDto {
    private String name;
    private ProductsCategories category;
    private int calories;
//    private List<Recipe> recipes;
}
