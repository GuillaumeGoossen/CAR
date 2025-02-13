package com.car.TP2.service;

import com.car.TP2.entity.Order;
import com.car.TP2.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.car.TP2.interfaces.OrderInterface;

/*
 * This class represents an order service
 */
@Service
public class OrderService implements OrderInterface {

    @Autowired
    private OrderRepository orderRepository;

    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    public List<Order> getOrdersByCustomerEmail(String email) {
        return orderRepository.findByCustomerEmail(email);
    }
}