package icu.com.demo.config;

import icu.com.demo.data.domain.Recipe;
import icu.com.demo.data.dto.RecipeDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper(){
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.createTypeMap(Recipe.class, RecipeDto.class).addMapping(Recipe::getProducts, RecipeDto::setProducts);
//        return modelMapper;
        return new ModelMapper();

    }
}
