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

    @PostMapping("/delete")
    public ModelAndView deleteOrder(@RequestParam Long orderId, HttpSession session) {
        String userId = getUserIdFromSession(session);
        if (userId == null) {
            return new ModelAndView("redirect:/store/home");
        }
        orderService.deleteOrder(orderId);
        return new ModelAndView("redirect:/store/user");
    }

    @GetMapping("/{title}")
    public ModelAndView viewOrder(@PathVariable String title, HttpSession session) {
        String userId = getUserIdFromSession(session);
        if (userId == null) {
            return new ModelAndView("redirect:/store/home");
        }
        Order order = orderService.findByTitleAndCustomerEmail(title, userId);
        if (order == null) {
            return new ModelAndView("redirect:/store/user");
        }
        var model = Map.of("order", order, "user", order.getCustomer());
        return new ModelAndView("store/orderDetails", model);
    }

    @PostMapping("/item/add")
public ModelAndView addItem(@RequestParam Long orderId, @RequestParam String wording, @RequestParam int quantity, @RequestParam double price, HttpSession session) {
    String userId = getUserIdFromSession(session);
    if (userId == null) {
        return new ModelAndView("redirect:/store/home");
    }
    orderService.addItemToOrder(orderId, wording, quantity, price);
    return new ModelAndView("redirect:/store/order/" + orderService.findById(orderId).getTitle());
}

@PostMapping("/item/delete")
public ModelAndView deleteItem(@RequestParam Long itemId, HttpSession session) {
    String userId = getUserIdFromSession(session);
    if (userId == null) {
        return new ModelAndView("redirect:/store/home");
    }
    Long orderId = orderService.deleteItemFromOrder(itemId);
    return new ModelAndView("redirect:/store/order/" + orderService.findById(orderId).getTitle());
}

@GetMapping("/imprimer/{title}")
public ModelAndView printOrder(@PathVariable String title, HttpSession session) {
    String userId = getUserIdFromSession(session);
    if (userId == null) {
        return new ModelAndView("redirect:/store/home");
    }
    Order order = orderService.findByTitleAndCustomerEmail(title, userId);
    if (order == null) {
        return new ModelAndView("redirect:/store/user");
    }
    double total = order.getItems().stream().mapToDouble(item -> item.getQuantity() * item.getPrice()).sum();
    var model = Map.of("order", order, "user", order.getCustomer(), "total", total);
    return new ModelAndView("store/printOrder", model);
}

    private String getUserIdFromSession(HttpSession session) {
        return (String) session.getAttribute("userId");
    }
}