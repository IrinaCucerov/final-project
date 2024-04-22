package icu.com.demo.services;

import icu.com.demo.data.domain.Product;
import icu.com.demo.data.dto.CreateProductDto;
import icu.com.demo.data.dto.ProductDto;
import icu.com.demo.data.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ModelMapper mapper;
    private final ProductRepository productRepository;


    public ProductService(ModelMapper modelMapper, ProductRepository productRepository) {
        this.mapper = modelMapper;
        this.productRepository = productRepository;
    }

    public ProductDto getProductById(long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Product not exist with id: " + id));
        return mapper.map(product, ProductDto.class);
//        return productRepository.findById(id).map(product -> mapper.map(product, ProductDto.class)).orElse(null);
    }

    public List<ProductDto> findAll(){
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> mapper.map(product, ProductDto.class))
                .collect(Collectors.toList());
    }

    public ProductDto createProduct(CreateProductDto createProductDto){
        Product newProduct = productRepository.save(mapper.map(createProductDto, Product.class));
        return mapper.map(newProduct, ProductDto.class);
    }

    public ProductDto updateProduct(ProductDto productDto, long id){
        Product existProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not exist with id: " + id));
        existProduct.setName(productDto.getName());
        existProduct.setCategory(productDto.getCategory());
        existProduct.setCalories(productDto.getCalories());
//        existProduct.setRecipes(productDto.getRecipes());
        productDto.setId(id);

        boolean isUpdateNeeded = existProduct.equals(mapper.map(productDto, Product.class));
        if(isUpdateNeeded){
            Product updateProduct = productRepository.save(mapper.map(existProduct, Product.class));
            return mapper.map(updateProduct, ProductDto.class);
        }
        return mapper.map(existProduct, ProductDto.class);
    }

     public void deleteProduct(long id){
        productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not exist with id: " + id));
        productRepository.deleteById(id);
    }

    public List<ProductDto> findProductsByRecipesId(long recipeId) {
        List<Product> products = productRepository.findProductsByRecipesId(recipeId);
        return products.stream()
                .map(x -> mapper.map(x, ProductDto.class))
                .collect(Collectors.toList());
    }

    public List<List<ProductDto>> findProductsByRecipesIdIn(List<Long> recipeIds){
        List<List<Product>> listOfProducts = recipeIds.stream()
                .map(productRepository::findProductsByRecipesId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (listOfProducts.isEmpty()){
            return Collections.emptyList();
        }

        List<List<ProductDto>> listOfProductSDto = listOfProducts.stream()
                .map(products -> products.stream()
                        .map(x -> mapper.map(x, ProductDto.class))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        return listOfProductSDto;
    }
}
