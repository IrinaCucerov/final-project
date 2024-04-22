package icu.com.demo.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import icu.com.demo.data.enums.ProductsCategories;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "Product")
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    @NotEmpty(message = "Not empty")
    private String name;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private ProductsCategories category;

    @Column(name = "calories")
    private int calories;

    @JsonIgnore
    @ManyToMany(
            fetch = FetchType.LAZY,
            mappedBy = "products")
    private List<Recipe> recipes = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return id == product.id && calories == product.calories
                && Objects.equals(name, product.name)
                && category == product.category
                && Objects.equals(recipes, product.recipes);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", calories=" + calories +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, category, calories, recipes);
    }
}
