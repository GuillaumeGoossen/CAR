package com.car.TP2.service;

import com.car.TP2.entity.Order;
import com.car.TP2.repository.OrderRepository;
import com.car.TP2.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.car.TP2.interfaces.OrderInterface;
import com.car.TP2.entity.User;

/*
 * This class represents an order service
 */
@Service
public class OrderService implements OrderInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void createOrder(String title, String userId) {
        User user = userRepository.findByEmail(userId);
        if (user != null) {
            Order order = new Order();
            order.setTitle(title);
            order.setCustomer(user);
            orderRepository.save(order);
        }
    }

    @Override
    public List<Order> getOrdersByCustomerEmail(String email) {
        return orderRepository.findByCustomerEmail(email);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }
}