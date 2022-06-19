package com.proiect2.product.services;

import com.proiect2.product.model.Product;

import java.util.List;

public interface ProductService {
    Product findByCategoryAndBrand(String category, String brand);
    Product save(Product product);
    List<Product> findAll();
    Product delete(Long id);

    Product findById(Long id);
}
