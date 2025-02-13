package com.car.TP2.controller;

import com.car.TP2.entity.Order;
import com.car.TP2.interfaces.OrderInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/store/order")
public class OrderController {

    @Autowired
    private OrderInterface orderService;

    @PostMapping("/create")
    public ModelAndView createOrder(@RequestParam String title, HttpSession session) {
        String userId = getUserIdFromSession(session);
        if (userId == null) {
            return new ModelAndView("redirect:/store/home");
        }
        orderService.createOrder(title, userId);
        return new ModelAndView("redirect:/store/user");
    }

    @GetMapping("/list")
    public ModelAndView listOrders(HttpSession session) {
        String userId = getUserIdFromSession(session);
        if (userId == null) {
            return new ModelAndView("redirect:/store/home");
        }
        List<Order> orders = orderService.getOrdersByCustomerEmail(userId);
        var model = Map.of("orders", orders);
        return new ModelAndView("store/user", model);
    }

    private String getUserIdFromSession(HttpSession session) {
        return (String) session.getAttribute("userId");
    }
}