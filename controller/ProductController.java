package com.proiect2.product.controller;

import com.proiect2.product.model.Product;
import com.proiect2.product.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;


    @GetMapping("/")
    String hello() {
        return "Welcome to our store!";
    }
    @GetMapping(value = "/product/list", produces = {"application/hal+json"})
    public CollectionModel<Product> findAll() {
        List<Product> subscriptions = productService.findAll();
        for (final Product subscription : subscriptions) {
            Link selfLink = linkTo(methodOn(ProductController.class).getProduct(subscription.getId())).withSelfRel();
            subscription.add(selfLink);
            Link deleteLink = linkTo(methodOn(ProductController.class).deleteProduct(subscription.getId())).withRel("deleteSubscription");
            subscription.add(deleteLink);
        }
        Link link = linkTo(methodOn(ProductController.class).findAll()).withSelfRel();
        CollectionModel<Product> result = CollectionModel.of(subscriptions, link);
        return result;
    }

    @GetMapping("/product/category/{category}/brand/{brand}")
    Product findByCategoryAndBrand(@PathVariable String category,
                                     @PathVariable String brand) {
        Product product = productService.findByCategoryAndBrand(category, brand);
        Link selfLink = linkTo(methodOn(ProductController.class).getProduct(product.getId())).withSelfRel();
        product.add(selfLink);
        return product;
    }


    @PostMapping("/product")
    public ResponseEntity<Product> save(@Valid @RequestBody Product product) {
        Product savedProduct = productService.save(product);
        URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{productId}").buildAndExpand(savedProduct.getId())
                .toUri();


        return ResponseEntity.created(locationUri).body(savedProduct);
    }


    @Operation(summary = "delete product by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "product deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content)})
    @DeleteMapping("/product/{productId}")
    public Product deleteProduct(@PathVariable Long subscriptionId) {

        Product product = productService.delete(subscriptionId);
        return product;
    }
    @GetMapping("/product/{productId}")
    public Product getProduct(@PathVariable Long productId) {

        Product product = productService.findById(productId);
        return product;

    }
}
