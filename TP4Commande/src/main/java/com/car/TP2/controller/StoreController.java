package com.car.TP2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/*
 * This class represents a store controller
 */
@Controller
@RequestMapping("/store")
public class StoreController {
    
    @GetMapping("/home")
    public ModelAndView home() {
        return new ModelAndView("/store/home");
    }
}
