 package com.uas.prodcosting.controllers;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// import com.uas.prodcosting.models.Login;
// import com.uas.prodcosting.services.LoginService;


// @Controller
// public class LoginController {
    
//     @Autowired
//     private LoginService loginService;

//     @GetMapping("registrasi")
//     public String regis(Model model){
//         model.addAttribute("regis", new Login());
//         return "registrasi";
//     }

//     @PostMapping("registrasi")
//     public String save(@ModelAttribute("regis") Login login, Model model){
//         if (login.getUsername().contains(" ")) {
//             model.addAttribute("usernameError", "Username tidak boleh mengandung spasi.");
//         } else if (login.getUsername().length() < 3 || login.getUsername().length() > 10) {
//             model.addAttribute("usernameError", "Username harus antara 3 hingga 10 karakter.");
//         }
//         if (login.getPassword().length() < 8 || login.getPassword().length() > 10) {
//             model.addAttribute("passwordError", "Password harus antara 8 hingga 10 karakter.");
//         }
//         if (model.containsAttribute("usernameError") || model.containsAttribute("passwordError")) {
//             return "registrasi";
//         }
//         loginService.save(login);
//         return "login";
//     }

//     @GetMapping("login")
//     public String login() {
//         return "login";
//     }

//     @GetMapping("/home2")
//     public String home2()
//         {
//             return "home";
//         }
    

//     @PostMapping("cek-login")
//     public String cekLogin(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
//         Login login = loginService.login(username, password);
//         if (login != null) {
//             model.addAttribute("login", login);
//             return "redirect:/home2";
//         } else {
//             return "redirect:/login";
//         }
//     }
// }

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
    public String regis(Model model) {
        model.addAttribute("regis", new Login());
        return "registrasi";
    }

    @PostMapping("registrasi")
    public String save(@ModelAttribute("regis") Login login, Model model) {
        // Validasi input
        boolean hasError = validateRegistration(login, model);

        // Jika ada error, kembali ke halaman registrasi
        if (hasError) {
            return "registrasi";
        }

        // Jika validasi lolos, simpan data ke database
        loginService.save(login);
        return "redirect:/login"; // Redirect ke halaman login setelah registrasi berhasil
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("/home2")
    public String home2() {
        return "home";
    }

    @PostMapping("cek-login")
    public String cekLogin(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        Login login = loginService.login(username, password);

        if (login != null) {
            model.addAttribute("login", login);
            return "redirect:/home2";
        } else {
            model.addAttribute("error", "Username atau password salah!");
            return "login";
        }
    }

    /**
     * Metode untuk validasi data registrasi.
     */
    private boolean validateRegistration(Login login, Model model) {
        boolean hasError = false;

        // Validasi username
        if (login.getUsername() == null || login.getUsername().isBlank()) {
            model.addAttribute("usernameError", "Username tidak boleh kosong.");
            hasError = true;
        } else if (login.getUsername().contains(" ")) {
            model.addAttribute("usernameError", "Username tidak boleh mengandung spasi.");
            hasError = true;
        } else if (login.getUsername().length() < 3 || login.getUsername().length() > 10) {
            model.addAttribute("usernameError", "Username harus antara 3 hingga 10 karakter.");
            hasError = true;
        }

        // Validasi password
        if (login.getPassword() == null || login.getPassword().isBlank()) {
            model.addAttribute("passwordError", "Password tidak boleh kosong.");
            hasError = true;
        } else if (login.getPassword().length() < 8 || login.getPassword().length() > 10) {
            model.addAttribute("passwordError", "Password harus antara 8 hingga 10 karakter.");
            hasError = true;
        }

        return hasError;
    }
}
