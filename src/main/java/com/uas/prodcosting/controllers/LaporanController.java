package com.uas.prodcosting.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LaporanController {

    @GetMapping("/laporan")
    public String 
    Laporan(Model model) {
       
        return "laporan"; 
    }
}