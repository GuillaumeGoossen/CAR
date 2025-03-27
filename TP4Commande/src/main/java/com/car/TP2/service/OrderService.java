package com.car.TP2.service;

import com.car.TP2.entity.Order;
import com.car.TP2.repository.OrderRepository;
import com.car.TP2.repository.UserRepository;
import com.car.TP2.repository.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import com.car.TP2.interfaces.OrderInterface;
import com.car.TP2.entity.User;
import com.car.TP2.entity.Item;

/*
 * This class represents an order service
 */
@Service
public class OrderService implements OrderInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "stock-update";

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
        return orderRepository.findByCustomerEmailAndStatus(email, "PENDING");
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public Order findByTitleAndCustomerEmail(String title, String email) {
        return orderRepository.findByTitleAndCustomerEmail(title, email);
    }

    @Override
    public void addItemToOrder(Long orderId, String wording, int quantity, double price) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            Item item = new Item();
            item.setWording(wording);
            item.setQuantity(quantity);
            item.setPrice(price);
            item.setOrder(order);
            order.getItems().add(item);
            itemRepository.save(item);
            orderRepository.save(order);
        }
    }

    @Override
    public Long deleteItemFromOrder(Long itemId) {
        Item item = itemRepository.findById(itemId).orElse(null);
        if (item != null) {
            Long orderId = item.getOrder().getId();
            itemRepository.delete(item);
            return orderId;
        }
        return null;
    }

    @Override
    public Order findById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public void submitOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            kafkaTemplate.send("stock-update", "Commande " + order.getTitle() + " soumise pour mise à jour des stocks.");
    
            order.setStatus("VALIDATED");
            orderRepository.save(order);
        }
    }
}