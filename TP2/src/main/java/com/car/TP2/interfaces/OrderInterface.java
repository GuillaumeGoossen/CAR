package com.car.TP2.interfaces;

import java.util.List;

import com.car.TP2.entity.Order;

public interface OrderInterface {
    public List<Order> getOrdersByCustomerEmail(String email);

    public void createOrder(String order, String userId);

    void deleteOrder(Long orderId);
}
