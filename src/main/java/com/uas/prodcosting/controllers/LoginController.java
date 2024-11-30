package com.uas.prodcosting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uas.prodcosting.models.Login;
import com.uas.prodcosting.services.LoginService;


@Controller
public class LoginController {
    
    @Autowired
    private LoginService loginService;

    @GetMapping("registrasi")
    public String regis(Model model){
        model.addAttribute("regis", new Login());
        return "registrasi";
    }

    @PostMapping("registrasi")
    public String save(@ModelAttribute("regis") Login login, Model model){
        // Validasi username
        if (login.getUsername().contains(" ")) {
            model.addAttribute("usernameError", "Username tidak boleh mengandung spasi.");
        } else if (login.getUsername().length() < 3 || login.getUsername().length() > 10) {
            model.addAttribute("usernameError", "Username harus antara 3 hingga 10 karakter.");
        }

        // Validasi password
        if (login.getPassword().length() < 8 || login.getPassword().length() > 10) {
            model.addAttribute("passwordError", "Password harus antara 8 hingga 10 karakter.");
        }

        // Jika ada error pada validasi, kembalikan ke halaman registrasi
        if (model.containsAttribute("usernameError") || model.containsAttribute("passwordError")) {
            return "registrasi";
        }

        // Jika validasi berhasil, simpan data login dan redirect ke login page
        loginService.save(login);
        return "login";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @PostMapping("cek-login")
    public String cekLogin(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        Login login = loginService.login(username, password);
        if (login != null) {
            model.addAttribute("login", login);
            return "home";
        } else {
            return "redirect:/login";
        }
    }
}
