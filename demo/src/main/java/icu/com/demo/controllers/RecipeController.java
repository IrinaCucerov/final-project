package icu.com.demo.controllers;

import icu.com.demo.data.dto.CreateRecipeDto;
import icu.com.demo.data.dto.RecipeDto;
import icu.com.demo.services.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getById(@PathVariable long id){
        RecipeDto recipeDto = recipeService.getRecipeById(id);
        if (Objects.isNull(recipeDto)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipeDto);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<RecipeDto>> findAll(){
        List<RecipeDto> recipes = recipeService.findAll();
        return ResponseEntity.ok(recipes);

    }

    @PostMapping("/new")
    public ResponseEntity<RecipeDto> createNewRecipe(@RequestBody CreateRecipeDto createRecipeDto){
        RecipeDto recipeDto = recipeService.createRecipe(createRecipeDto);
        return ResponseEntity
                .created(ServletUriComponentsBuilder
                        .fromCurrentRequest().path("/{id}")
                        .buildAndExpand(recipeDto).toUri()).body(recipeDto);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<RecipeDto> updateRecipe(@RequestBody RecipeDto recipeDto,
                                                  @PathVariable long id){
        RecipeDto updateRecipe = recipeService.updateRecipe(recipeDto, id);
        return ResponseEntity.ok(updateRecipe);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable long id){
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/findRecipes/{productId}")
    public ResponseEntity<List<RecipeDto>> findRecipesByProductsId(@PathVariable long productId){
        List<RecipeDto> recipes = recipeService.findRecipesByProductsId(productId);
        if (Objects.isNull(recipes)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/findAllRecipes/{productIds}")
    public ResponseEntity<List<List<RecipeDto>>> findRecipesByProductsIdIn(@PathVariable List<Long> productIds){
        List<List<RecipeDto>> listOfRecipes = recipeService.findRecipesByProductsIdIn(productIds);
        if (Objects.isNull(listOfRecipes)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listOfRecipes);
    }

}
