package com.example.crud.crud.config;

import com.example.crud.crud.repository.BrandRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class BrandConfig {

    @Bean
    CommandLineRunner brandRunner(BrandRepository brandRepository) {
        return args -> {
            brandRepository.saveAll(List.of(BrandsEnum.FIAT.getBrand(), BrandsEnum.HONDA.getBrand()));
        };
    }
}