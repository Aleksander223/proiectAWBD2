package com.proiect2.order.services;

import com.proiect2.order.model.Order;
import com.proiect2.order.model.OrderItem;

import java.util.List;

public interface OrderService {
    Order create(Order order);
    List<Order> findAll();

    Order findById(Long id);
}
