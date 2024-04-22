package icu.com.demo.data.repository;


import icu.com.demo.data.domain.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public interface ProductRepository extends JpaRepository <Product, Long>{
   List<Product> findProductsByRecipesId(Long recipeId);

//   List<Product> findProductsByRecipesIdIn(List<Long> recipeIds);
}
