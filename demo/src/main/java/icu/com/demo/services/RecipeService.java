package icu.com.demo.services;

import icu.com.demo.data.domain.Recipe;
import icu.com.demo.data.dto.CreateRecipeDto;
import icu.com.demo.data.dto.RecipeDto;
import icu.com.demo.data.repository.RecipeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    private final ModelMapper mapper;
    private final RecipeRepository recipeRepository;

    public RecipeService(ModelMapper mapper, RecipeRepository recipeRepository) {
        this.mapper = mapper;
        this.recipeRepository = recipeRepository;
    }

    public RecipeDto getRecipeById(long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe is not exist with id: " + id));
        return mapper.map(recipe, RecipeDto.class);
    }

    public List<RecipeDto> findAll() {
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes.stream()
                .map(recipe -> mapper.map(recipe, RecipeDto.class))
                .collect(Collectors.toList());
    }

    public RecipeDto createRecipe(CreateRecipeDto createRecipeDto) {
        Recipe newRecipe = recipeRepository.save(mapper.map(createRecipeDto, Recipe.class));
        return mapper.map(newRecipe, RecipeDto.class);
    }

    public RecipeDto updateRecipe(RecipeDto recipeDto, long id) {
        Recipe existRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not exist with id: " + id));

        boolean isUpdateNeeded = !existRecipe.equals(mapper.map(recipeDto, Recipe.class));
        if (isUpdateNeeded) {

            if (recipeDto.getName() != null && !Objects.equals(recipeDto.getName(), existRecipe.getName())) {
                existRecipe.setName(recipeDto.getName());
            }
            if (recipeDto.getDescription() != null && !Objects.equals(recipeDto.getDescription(), existRecipe.getDescription())) {
                existRecipe.setDescription(recipeDto.getDescription());
            }
            if (recipeDto.getCategory() !=null && !Objects.equals(recipeDto.getCategory(), existRecipe.getCategory())){
                existRecipe.setCategory(recipeDto.getCategory());
            }
            if (recipeDto.getCalories() !=0 && !Objects.equals(recipeDto.getCalories(), existRecipe.getCalories())){
                existRecipe.setCalories(recipeDto.getCalories());
            }

            Recipe updateRecipe = recipeRepository.save(mapper.map(existRecipe, Recipe.class));
            return mapper.map(updateRecipe, RecipeDto.class);
        }

        return mapper.map(existRecipe, RecipeDto.class);
    }

    public void deleteRecipe(long id) {
        recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not exist with id: " + id));
        recipeRepository.deleteById(id);

    }


    public List<RecipeDto> findRecipesByProductsId(long productId) {
        List<Recipe> recipes = recipeRepository.findRecipesByProductsId(productId);
        return recipes.stream()
                .map(x->mapper.map(x, RecipeDto.class))
                .collect(Collectors.toList());
    }

    public List<List<RecipeDto>> findRecipesByProductsIdIn(List<Long> productIds) {
        List<List<Recipe>> listOfRecipe = productIds.stream()
                .map(recipeRepository::findRecipesByProductsId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (listOfRecipe.isEmpty()){
            return Collections.emptyList();
        }
        List<List<RecipeDto>> listOfRecipeDto = listOfRecipe.stream()
                .map(recipes -> recipes.stream()
                .map(x -> mapper.map(x, RecipeDto.class))
                .collect(Collectors.toList()))
                .collect(Collectors.toList());
        return listOfRecipeDto;

    }
}
