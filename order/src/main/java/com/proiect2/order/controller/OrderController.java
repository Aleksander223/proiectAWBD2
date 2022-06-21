package com.proiect2.order.controller;

import com.proiect2.order.model.Order;
import com.proiect2.order.model.OrderItem;
import com.proiect2.order.model.PropertiesConfig;
import com.proiect2.order.services.OrderService;
import com.proiect2.order.services.ProductServiceProxy;
import com.proiect2.order.model.Product;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    ProductServiceProxy productServiceProxy;

    @Autowired
    PropertiesConfig configuration;


    @GetMapping("/")
    String hello() {
        return "TVA " + configuration.getTva();
    }
    @GetMapping(value = "/order/list", produces = {"application/hal+json"})
    public CollectionModel<Order> findAll() {
        List<Order> subscriptions = orderService.findAll();
        for (final Order subscription : subscriptions) {
            Link selfLink = linkTo(methodOn(OrderController.class).getOrder(subscription.getId())).withSelfRel();
            subscription.add(selfLink);
        }
        Link link = linkTo(methodOn(OrderController.class).findAll()).withSelfRel();
        CollectionModel<Order> result = CollectionModel.of(subscriptions, link);
        return result;
    }


    @PostMapping("/order")
    public ResponseEntity<Order> save(@Valid @RequestBody List<OrderItem> items) {
        float total = 0.0f;

        for (OrderItem orderItem: items) {
            Product product = productServiceProxy.getProduct(orderItem.getId());

            total += product.getPrice();
        }

        total = total + configuration.getTva() * total;

        Order savedOrder = orderService.create(new Order(total));
        URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{orderId}").buildAndExpand(savedOrder.getId())
                .toUri();


        return ResponseEntity.created(locationUri).body(savedOrder);
    }


    @GetMapping("/order/{orderId}")
    @CircuitBreaker(name="orderId", fallbackMethod = "getOrderFallback")
    public Order getOrder(@PathVariable Long orderId) {

        Order order = orderService.findById(orderId);
        return order;

    }

    private Order getOrderFallback(Long orderId, Throwable throwable) {
        Order order = orderService.findById(orderId);
        return order;
    }
}
