package icu.com.demo.controllers;

import icu.com.demo.data.domain.Product;
import icu.com.demo.data.dto.CreateProductDto;
import icu.com.demo.data.dto.ProductDto;
import icu.com.demo.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable long id){
        ProductDto productDto = productService.getProductById(id);
        if (Objects.isNull(productDto)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<ProductDto>> findAll(){
        List<ProductDto> products = productService.findAll();
        return ResponseEntity.ok(products);

    }


    @PostMapping("/new")
    public ResponseEntity<ProductDto> createNewProduct(@RequestBody CreateProductDto createProductDto) {
        ProductDto productDto = productService.createProduct(createProductDto);
        return ResponseEntity
                .created(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(productDto).toUri()).body(productDto);
    }
    @PatchMapping("/update/{id}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,
                                                    @PathVariable long id){
        ProductDto updateProduct = productService.updateProduct(productDto, id);
        return ResponseEntity.ok(updateProduct);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/findProducts/{recipeId}")
    public ResponseEntity<List<ProductDto>> findProductsByRecipeId(@PathVariable Long recipeId){
        List<ProductDto> products = productService.findProductsByRecipesId(recipeId);
        if (Objects.isNull(products)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/findAllProducts/{recipeIds}")
    public ResponseEntity<List<List<ProductDto>>> findProductsByRecipesIdIn(@PathVariable List<Long> recipeIds){
        List<List<ProductDto>> listOfProducts = productService.findProductsByRecipesIdIn(recipeIds);
        if (Objects.isNull(listOfProducts)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listOfProducts);
    }

}
