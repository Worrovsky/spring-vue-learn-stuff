package com.example.demo.service;

import com.example.demo.model.Product;

import java.util.List;
import java.util.Optional;

public interface DBService {

    public List<Product> getAllProducts();

    public Optional<Product> findProductById(Long id);

    public void deleteProductById(Long id);

    public Product saveProduct(Product p);

}
