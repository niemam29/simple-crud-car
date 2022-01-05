package com.example.crud.crud.config;

import com.example.crud.crud.entity.Brand;
import com.example.crud.crud.repository.BrandRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class BrandConfig {
    @Bean
    CommandLineRunner brandRunner(BrandRepository brandRepository) {
        return args -> {
            Brand fiat = new Brand(
                    "Fiat",
                    "Corporate Address\n" +
                            "Stellantis N.V.\n" +
                            "Taurusavenue 1\n" +
                            "2132 LS Hoofddorp, The Netherlands",
                    LocalDate.of(1923, 10, 12));

            Brand honda = new Brand(
                    "Honda",
                    "2-1-1 Minami-Aoyama,\n" +
                            " Minato-ku, \n" +
                            "Tokyo, \n" +
                            "107-8556,\n" +
                            "Japan",
                    LocalDate.of(1923, 10, 12));

            brandRepository.saveAll(List.of(fiat, honda));
        };
    }
}