package com.car.TP2.controller;

import com.car.TP2.entity.Order;
import com.car.TP2.entity.User;
import com.car.TP2.service.OrderService;
import com.car.TP2.service.UserService;
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
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ModelAndView createOrder(@RequestParam String title, HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView("redirect:/store/home");
        }
        User user = userService.findByEmail(userId);
        Order order = new Order();
        order.setTitle(title);
        order.setCustomer(user);
        orderService.createOrder(order);
        return new ModelAndView("redirect:/store/user");
    }

    @GetMapping("/list")
    public ModelAndView listOrders(HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView("redirect:/store/home");
        }
        List<Order> orders = orderService.getOrdersByCustomerEmail(userId);
        var model = Map.of("orders", orders);
        return new ModelAndView("store/user", model);
    }
}