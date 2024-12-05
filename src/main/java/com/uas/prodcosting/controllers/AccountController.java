package com.uas.prodcosting.controllers;

import com.uas.prodcosting.models.Account;
import com.uas.prodcosting.services.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Menampilkan daftar akun
    @GetMapping
    public String listAccounts(Model model) {
        List<Account> accounts = accountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        return "list-accounts";
    }

    // Menampilkan form untuk menambah akun
    @GetMapping("/add")
    public String addAccountForm(Model model) {
        model.addAttribute("account", new Account());
        return "add-account";
    }

    // Menyimpan akun baru
    @PostMapping("/save")
    public String saveAccount(@ModelAttribute Account account) {
        accountService.createAccount(account);
        return "redirect:/accounts";
    }

    // Menampilkan form untuk mengedit akun
    @GetMapping("/edit/{id}")
    public String editAccountForm(@PathVariable Long id, Model model) {
        Account account = accountService.getAccountById(id);
        model.addAttribute("account", account);
        return "edit-account";
    }

    // Menyimpan perubahan akun
    @PostMapping("/update")
    public String updateAccount(@ModelAttribute Account account) {
        accountService.createAccount(account);  // Menggunakan method yang sama untuk menyimpan perubahan
        return "redirect:/accounts";
    }

    // Menghapus akun
    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return "redirect:/accounts";
    }


}