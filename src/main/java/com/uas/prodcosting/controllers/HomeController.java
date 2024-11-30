package com.uas.prodcosting.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
        String name = "User"; 
        model.addAttribute("name", name); 
        return "home"; 
    }
}