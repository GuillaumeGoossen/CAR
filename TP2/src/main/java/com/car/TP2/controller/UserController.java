package com.car.TP2.controller;

import com.car.TP2.entity.User;
import com.car.TP2.interfaces.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/store/user")
public class UserController {

    @Autowired
    private UserInterface userService;

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        User user = userService.login(email, password);
        if (user != null) {
            session.setAttribute("userId", user.getEmail());
            return new ModelAndView("redirect:/store/user");
        } else {
            ModelAndView mav = new ModelAndView("store/home");
            mav.addObject("error", "Invalid email or password");
            return mav;
        }
    }

    @PostMapping("/signin")
    public ModelAndView signin(@RequestParam String email, @RequestParam String password, @RequestParam String name, @RequestParam String surname, HttpSession session) {
        if (userService.existsByEmail(email)) {
            ModelAndView mav = new ModelAndView("store/home");
            mav.addObject("error", "Account already exists");
            return mav;
        } else {
            userService.createUser(email, password, name, surname);
            User user = userService.findByEmail(email);
            session.setAttribute("userId", user.getEmail());
            return new ModelAndView("redirect:/store/home");
        }
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        return new ModelAndView("redirect:/store/home");
    }

    @GetMapping
    public ModelAndView home(HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return new ModelAndView("redirect:/store/home");
        }
        User user = userService.findByEmail(userId);
        var model = Map.of("user", user);
        return new ModelAndView("store/user", model);
    }
}