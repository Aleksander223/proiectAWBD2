package com.proiect2.order.services;

import com.proiect2.order.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "product", url="localhost:8080")
public interface ProductServiceProxy {
    @GetMapping("/product/{productId}")
    public Product getProduct(@PathVariable Long productId);
}
