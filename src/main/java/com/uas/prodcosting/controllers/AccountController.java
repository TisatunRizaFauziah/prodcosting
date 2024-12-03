package com.uas.prodcosting.controllers;

import com.uas.prodcosting.models.Account;
import com.uas.prodcosting.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public String listAccounts(Model model) {
        List<Account> accounts = accountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        return "list-accounts"; // Ganti dengan nama template yang sesuai
    }

    @GetMapping("/add")
    public String createForm(Model model) {
        model.addAttribute("account", new Account());
        return "add-account"; // Ganti dengan nama template untuk form penambahan akun
    }

    @PostMapping("/save")
    public String saveAccount(@ModelAttribute Account account) {
        accountService.saveAccount(account);
        return "redirect:/accounts"; // Redirect ke daftar akun setelah disimpan
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Account account = accountService.getAccountById(id).orElse(null);
        model.addAttribute("account", account);
        return "edit-account"; // Ganti dengan nama template untuk form edit akun
    }

    @PostMapping("/update/{id}")
    public String updateAccount(@PathVariable Long id, @ModelAttribute Account account) {
        account.setId(id); // Set ID untuk memastikan entitas yang benar diperbarui
        accountService.saveAccount(account);
        return "redirect:/accounts"; // Redirect ke daftar akun setelah diperbarui
    }

    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return "redirect:/accounts"; // Redirect ke daftar akun setelah dihapus
    }
}