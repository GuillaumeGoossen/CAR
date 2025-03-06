package com.car.TP2.interfaces;

import com.car.TP2.entity.Order;
import java.util.List;

public interface OrderInterface {
    void createOrder(String title, String userId);
    List<Order> getOrdersByCustomerEmail(String email);
    void deleteOrder(Long orderId);
    Order findByTitleAndCustomerEmail(String title, String email);
    void addItemToOrder(Long orderId, String wording, int quantity, double price);
    Long deleteItemFromOrder(Long itemId);
    Order findById(Long orderId);
}