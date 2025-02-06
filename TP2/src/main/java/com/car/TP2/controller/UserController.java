package com.car.TP2.controller;

import com.car.TP2.entity.User;
import com.car.TP2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/store/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String email, @RequestParam String password) {
        User user = userService.login(email, password);
        if (user != null) {
            return new ModelAndView("redirect:/store/user?email=" + email);
        } else {
            ModelAndView mav = new ModelAndView("store/home");
            mav.addObject("error", "Invalid email or password");
            return mav;
        }
    }

    @PostMapping("/signin")
    public ModelAndView signin(@RequestParam String email, @RequestParam String password, @RequestParam String name, @RequestParam String surname) {
        if (userService.existsByEmail(email)) {
            ModelAndView mav = new ModelAndView("store/home");
            mav.addObject("error", "Account already exists");
            return mav;
        } else {
            User user = new User(email, password, name, surname);
            userService.save(user);
            return new ModelAndView("redirect:/store/home");
        }
    }

    @GetMapping("/logout")
    public ModelAndView logout() {
        return new ModelAndView("redirect:/store/home");
    }

    @GetMapping
    public ModelAndView home(@RequestParam String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return new ModelAndView("redirect:/store/home");
        }
        var model = Map.of("user", user);
        return new ModelAndView("store/user", model);
    }
}