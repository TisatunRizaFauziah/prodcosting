package com.uas.prodcosting.controllers;

import com.uas.prodcosting.models.JournalEntry;
import com.uas.prodcosting.models.Company; // Import Company
import com.uas.prodcosting.models.Journal; // Import Journal
import com.uas.prodcosting.models.Account; // Import Account
import com.uas.prodcosting.services.JournalEntryService;
import com.uas.prodcosting.services.CompanyService; // Import CompanyService
import com.uas.prodcosting.services.JournalService; // Import JournalService
import com.uas.prodcosting.services.AccountService; // Import AccountService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @Controller
// @RequestMapping("/journal-entries")
// public class JournalEntryController {

//     @Autowired
//     private JournalEntryService journalEntryService;

//     @Autowired
//     private CompanyService companyService; 

//     @Autowired
//     private JournalService journalService; 

//     @Autowired
//     private AccountService accountService; 

//     @GetMapping
//     public String listJournalEntries(Model model) {
//         List<JournalEntry> journalEntries = journalEntryService.getAllJournalEntries();
//         model.addAttribute("journalEntries", journalEntries);
//         return "list-journal-entries"; 
//     }

//     // @GetMapping("/add")
//     // public String createForm(Model model) {
//     //     model.addAttribute("journalEntry", new JournalEntry());
//     //     List<Company> companies = companyService.getAllCompanies(); 
//     //     List<Journal> journals = journalService.getAllJournals(); 
//     //     List<Account> accounts = accountService.getAllAccounts(); 
//     //     model.addAttribute("companies", companies); 
//     //     model.addAttribute("journals", journals); 
//     //     model.addAttribute("accounts", accounts); 
//     //     return "add-journal-entry"; 
//     // }

//     // @PostMapping("/save")
//     // public String saveJournalEntry(@ModelAttribute JournalEntry journalEntry) {
//     //     journalEntryService.saveJournalEntry(journalEntry);
//     //     return "redirect:/journal-entries"; // Redirect ke daftar entri jurnal setelah disimpan
//     // }

//     @GetMapping("/add")
//     public String createForm(Model model) {
//         model.addAttribute("journalEntry", new JournalEntry());
//         List<Company> companies = companyService.getAllCompanies();
//         List<Journal> journals = journalService.getAllJournals();
//         List<Account> accounts = accountService.getAllAccounts();
//         model.addAttribute("companies", companies);
//         model.addAttribute("journals", journals);
//         model.addAttribute("accounts", accounts);

//         return "add-journal-entry"; // View name
//     }

//     @PostMapping("/save")
//     public String saveJournalEntry(@ModelAttribute("journalEntry") JournalEntry journalEntry) {
//         // System.out.println("Account ID: " + journalEntry.getAccount().getId());
//         System.out.println("Company ID: " + journalEntry.getCompany().getId());
//         System.out.println("Journal ID: " + journalEntry.getJournal().getId());
//         journalEntryService.saveJournalEntry(journalEntry);
//         return "redirect:/journal-entries"; // Redirect after saving
//     }

//     @GetMapping("/edit/{id}")
//     public String editForm(@PathVariable Long id, Model model) {
//         JournalEntry journalEntry = journalEntryService.getJournalEntryById(id).orElse(null);
//         if (journalEntry == null) {
//             // Jika tidak ditemukan, redirect atau tampilkan error
//             return "redirect:/journal-entries"; // Atau tampilkan error
//         }
//         model.addAttribute("journalEntry", journalEntry);
//         List<Company> companies = companyService.getAllCompanies(); // Ambil daftar perusahaan
//         List<Journal> journals = journalService.getAllJournals(); // Ambil daftar jurnal
//         List<Account> accounts = accountService.getAllAccounts(); // Ambil daftar akun
//         model.addAttribute("companies", companies); // Tambahkan ke model
//         model.addAttribute("journals", journals); // Tambahkan ke model
//         model.addAttribute("accounts", accounts); // Tambahkan ke model
//         return "edit-journal-entry"; // Ganti dengan nama template untuk form edit entri jurnal
//     }

//     @PostMapping("/update/{id}")
//     public String updateJournalEntry(@PathVariable Long id, @ModelAttribute JournalEntry journalEntry) {
//         journalEntry.setId(id); // Set ID untuk memastikan entitas yang benar diperbarui
//         journalEntryService.saveJournalEntry(journalEntry);
//         return "redirect:/journal-entries"; // Redirect ke daftar entri jurnal setelah diperbarui
//     }

//     @GetMapping("/delete/{id}")
//     public String deleteJournalEntry(@PathVariable Long id) {
//         journalEntryService.deleteJournalEntry(id);
//         return "redirect:/journal-entries"; // Redirect ke daftar entri jurnal setelah dihapus
//     }
// }

@Controller
@RequestMapping("/journal-entries")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private CompanyService companyService; 

    @Autowired
    private JournalService journalService; 

    @Autowired
    private AccountService accountService; 

    @GetMapping
    public String listJournalEntries(Model model) {
        List<JournalEntry> journalEntries = journalEntryService.getAllJournalEntries();
        model.addAttribute("journalEntries", journalEntries);
        return "list-journal-entries"; 
    }

    @GetMapping("/add")
    public String createForm(Model model) {
        model.addAttribute("journalEntry", new JournalEntry());
        // List<Company> companies = companyService.getAllCompanies();
        // List<Journal> journals = journalService.getAllJournals();
        // List<Account> accounts = accountService.getAllAccounts();
        // model.addAttribute("companies", companies);
        // model.addAttribute("journals", journals);
        // model.addAttribute("accounts", accounts);
        return "add-journal-entry";
    }

    @PostMapping("/save")
    public String saveJournalEntry(@ModelAttribute("journalEntry") JournalEntry journalEntry) {
        // Pastikan semua relasi sudah diinisialisasi
        // System.out.println("Company ID: " + journalEntry.getCompany().getId());
        // System.out.println("Journal ID: " + journalEntry.getJournal().getId());
        // System.out.println("Account ID: " + journalEntry.getAccount().getId());
        journalEntryService.saveJournalEntry(journalEntry);
        return "redirect:/journal-entries";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        JournalEntry journalEntry = journalEntryService.getJournalEntryById(id).orElse(null);
        if (journalEntry == null) {
            return "redirect:/journal-entries";
        }
        model.addAttribute("journalEntry", journalEntry);
        List<Company> companies = companyService.getAllCompanies();
        List<Journal> journals = journalService.getAllJournals();
        List<Account> accounts = accountService.getAllAccounts();
        model.addAttribute("companies", companies);
        model.addAttribute("journals", journals);
        model.addAttribute("accounts", accounts);
        return "edit-journal-entry";
    }

    @PostMapping("/update/{id}")
    public String updateJournalEntry(@PathVariable Long id, @ModelAttribute JournalEntry journalEntry) {
        journalEntry.setId(id);
        journalEntryService.saveJournalEntry(journalEntry);
        return "redirect:/journal-entries";
    }

    @GetMapping("/delete/{id}")
    public String deleteJournalEntry(@PathVariable Long id) {
        journalEntryService.deleteJournalEntry(id);
        return "redirect:/journal-entries";
    }
}
