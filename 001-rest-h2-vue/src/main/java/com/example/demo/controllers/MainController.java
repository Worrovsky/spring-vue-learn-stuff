package com.example.demo.controllers;

import com.example.demo.model.Product;
import com.example.demo.service.DBService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MainController {

    private final DBService dbService;

    public MainController(DBService dbService) {
        this.dbService = dbService;
    }

    @GetMapping("/products")
    public List<Product> getAll() {
        List<Product> products = dbService.getAllProducts();
        return products;
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) {
        Optional<Product> productOptional = dbService.findProductById(id);
        Product p = productOptional.orElse(null);
        if (p == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(p, HttpStatus.OK);
        }
    }

    @PutMapping("/products/{id}")
    public Product updateProduct(@RequestBody Product newProduct, @PathVariable Long id) {
        Optional<Product> productOptional = dbService.findProductById(id);
        Product p = productOptional.orElse(null);
        if (p != null) {
            p.setName(newProduct.getName());
            dbService.saveProduct(p);
            return p;
        } else {
            newProduct.setId(id);
            dbService.saveProduct(newProduct);
            return newProduct;
        }
    }

    @PostMapping("/products")
    public Product addProduct(@RequestBody Product newProduct) {
        return dbService.saveProduct(newProduct);
    }

    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable Long id) {
        dbService.deleteProductById(id);
    }
}
