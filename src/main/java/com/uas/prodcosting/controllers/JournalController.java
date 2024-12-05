
package com.uas.prodcosting.controllers;

import com.uas.prodcosting.models.Account;
import com.uas.prodcosting.models.Company;
import com.uas.prodcosting.models.Journal;
import com.uas.prodcosting.services.AccountService;
import com.uas.prodcosting.services.CompanyService;
import com.uas.prodcosting.services.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/journals")
public class JournalController {

    @Autowired
    private JournalService journalService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private AccountService accountService;

    // Menampilkan daftar jurnal
    @GetMapping
    public String listJournals(Model model) {
        List<Journal> journals = journalService.getAllJournals();
        model.addAttribute("journals", journals);
        return "list-journals";
    }
    
    //   @GetMapping("/company/{id}")
    // public String listJournalsByCompany(@PathVariable Long companyId, Model model) {
    //     List<Journal> journals = journalService.getJournalsByCompanyId(companyId);
    //     Company company = companyService.getCompanyById(companyId);
    //     model.addAttribute("journals", journals);
    //     model.addAttribute("company", company);
    //     return "list-journals-by-company";
    // }

    @GetMapping("/company/{companyId}")
public String listJournalsByCompany(@PathVariable Long companyId, Model model) {
    List<Journal> journals = journalService.getJournalsByCompanyId(companyId);
    Company company = companyService.getCompanyById(companyId);
    model.addAttribute("journals", journals);
    model.addAttribute("company", company);
    return "list-journals-by-company";
}



    @GetMapping("/add")
    public String addJournalForm(Model model) {
        List<Company> companies = companyService.getAllCompanies();
        List<Account> accounts = accountService.getAllAccounts();  // Mendapatkan semua akun

        model.addAttribute("companies", companies);
        model.addAttribute("accounts", accounts);  // Menambahkan data akun ke model
        model.addAttribute("journal", new Journal());  // Menambahkan objek Journal untuk form
        return "add-journal";
    }

    // Menyimpan jurnal baru atau memperbarui jurnal
    @PostMapping("/save")
    public String saveJournal(@ModelAttribute Journal journal) {
        
            journalService.createJournal(journal.getCompany().getId(), 
                                         journal.getDebitAccount().getId(), 
                                         journal.getCreditAccount().getId(), 
                                         journal);
        
        
        return "redirect:/journals";
    }
        @GetMapping("/update/{id}")
public String updateJournal(@PathVariable Long id, Model model) {
    Journal journal = journalService.getJournalById(id);
      

    model.addAttribute("companies", companyService.getAllCompanies());
    model.addAttribute("account", accountService.getAllAccounts());
    model.addAttribute("journal", journal); 
    return "edit-journal";
}

@PostMapping("/update/{id}")
public String saveUpdate(@PathVariable Long id, @ModelAttribute("journal")Journal journal) {

   Journal journals = journalService.getJournalById(id);
   journals.setCompany(journal.getCompany());
   journals.setTransactionDate(journal.getTransactionDate());
   journals.setDescription(journal.getDescription());
   journals.setDebitAccount(journal.getDebitAccount());
   journals.setDebitAmount(journal.getDebitAmount());
   journals.setCreditAccount(journal.getCreditAccount());
   journals.setCreditAmount(journal.getCreditAmount());

   journalService.saveJournal(journals);
    return "redirect:/journals";
}



    // Menghapus jurnal
    @GetMapping("/delete/{id}")
    public String deleteJournal(@PathVariable Long id) {
        journalService.deleteJournal(id);
        return "redirect:/journals";
    }
}
