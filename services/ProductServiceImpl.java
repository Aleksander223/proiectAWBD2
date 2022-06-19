package com.proiect2.product.services;

import com.proiect2.product.exceptions.ProductNotFound;
import com.proiect2.product.model.Product;
import com.proiect2.product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductRepository productRepository;

    @Override
    public Product findByCategoryAndBrand(String category, String brand) {
        Product product = productRepository.findByCategoryAndBrand(category,brand);
        return product;
    }
    @Override
    public Product save(Product product) {
        Product productSave = productRepository.save(product);
        return productSave;
    }

    @Override
    public List<Product> findAll(){
        List<Product> products = new LinkedList<>();
        productRepository.findAll().iterator().forEachRemaining(products::add);
        return products;
    }

    @Override
    public Product delete(Long id){
        Optional<Product> productOptional = productRepository.findById(id);
        if (! productOptional.isPresent())
            throw new ProductNotFound("Product " + id + " not found!");
        productRepository.delete(productOptional.get());
        return productOptional.get();
    }

    @Override
    public Product findById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (! productOptional.isPresent())
            throw new ProductNotFound("Product " + id + " not found!");
        return productOptional.get();
    }
}
