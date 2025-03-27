package com.car.TP2.repository;

import com.car.TP2.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/*
 * This interface represents an order repository
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerEmail(String email);

    Order findByTitleAndCustomerEmail(String title, String email);

    List<Order> findByCustomerEmailAndStatus(String email, String status);

}