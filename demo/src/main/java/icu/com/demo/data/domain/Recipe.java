package icu.com.demo.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import icu.com.demo.data.enums.CategoriesOfDishes;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "Recipe")
public class Recipe {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Column(name = "name")
    @NotEmpty(message = "not empty")
    private String name;

    @Column(name = "description")
    @NotEmpty(message = "not empty")
    private String description;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private CategoriesOfDishes category;

    @Column(name = "calories")
    private int calories;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "recipe_products",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipe recipe)) return false;
        return id == recipe.id && calories == recipe.calories
                && Objects.equals(name, recipe.name)
                && Objects.equals(description, recipe.description)
                && category == recipe.category
                && Objects.equals(products, recipe.products);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", calories=" + calories +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, category, calories, products);
    }
}
