package com.proiect2.product.repositories;

import com.proiect2.product.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
    Product findByCategoryAndBrand(String category, String brand);
}
