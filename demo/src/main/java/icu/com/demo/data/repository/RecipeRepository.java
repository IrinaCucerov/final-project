package icu.com.demo.data.repository;

import icu.com.demo.data.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,Long> {
    List<Recipe> findRecipesByProductsId(Long productId);

//    List<Recipe> findRecipesBy
}
