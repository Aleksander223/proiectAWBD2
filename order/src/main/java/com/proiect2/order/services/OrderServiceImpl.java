package com.proiect2.order.services;

import com.proiect2.order.model.Order;
import com.proiect2.order.exceptions.OrderNotFound;
import com.proiect2.order.model.OrderItem;
import com.proiect2.order.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Override
    public Order create(Order order) {
        Order orderSave = orderRepository.save(order);
        return orderSave;
    }

    @Override
    public List<Order> findAll(){
        List<Order> orders = new LinkedList<>();
        orderRepository.findAll().iterator().forEachRemaining(orders::add);
        return orders;
    }

    @Override
    public Order findById(Long id) {
        Optional<Order> productOptional = orderRepository.findById(id);
        if (! productOptional.isPresent())
            throw new OrderNotFound("Order " + id + " not found!");
        return productOptional.get();
    }
}
