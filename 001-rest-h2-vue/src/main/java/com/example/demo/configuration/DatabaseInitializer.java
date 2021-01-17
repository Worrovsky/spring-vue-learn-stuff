package com.example.demo.configuration;

import com.example.demo.model.Product;
import com.example.demo.service.DBService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInitializer {

    @Bean
    public CommandLineRunner initDatabase(DBService dbService) {
        return args -> {
            dbService.saveProduct(new Product("Milk"));
            dbService.saveProduct(new Product("Chocolade"));

            System.out.println("db initialized");
        };
    }

}
