package com.car.TP2.controller;

import com.car.TP2.entity.Order;
import com.car.TP2.entity.User;
import com.car.TP2.interfaces.UserInterface;
import com.car.TP2.interfaces.OrderInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import java.util.Map;
import java.util.List;

@Controller
@RequestMapping("/store/user")
public class UserController {

    @Autowired
    private UserInterface userService;

    @Autowired
    private OrderInterface orderService;

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        userService.handleLogin(email, password, session);
        if (session.getAttribute("userId") != null) {
            return new ModelAndView("redirect:/store/user");
        } else {
            ModelAndView mav = new ModelAndView("store/home");
            mav.addObject("error", "Invalid email or password");
            return mav;
        }
    }

    @PostMapping("/signin")
    public ModelAndView signin(@RequestParam String email, @RequestParam String password, @RequestParam String name, @RequestParam String surname, HttpSession session) {
        if (!userService.handleSignin(email, password, name, surname, session)) {
            ModelAndView mav = new ModelAndView("store/home");
            mav.addObject("error", "Account already exists");
            return mav;
        } else {
            return new ModelAndView("redirect:/store/user");
        }
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        userService.handleLogout(session);
        return new ModelAndView("redirect:/store/home");
    }

    @GetMapping
    public ModelAndView home(HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView("redirect:/store/home");
        }
        User user = userService.findByEmail(userId);
        List<Order> orders = orderService.getOrdersByCustomerEmail(userId);
        var model = Map.of("user", user, "orders", orders);
        return new ModelAndView("store/user", model);
    }
}