package com.uas.prodcosting.controllers;

import com.uas.prodcosting.models.Journal;
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

    @GetMapping
    public String listJournals(Model model) {
        List<Journal> journals = journalService.getAllJournals();
        model.addAttribute("journals", journals);
        return "list-journals"; // Ganti dengan nama template yang sesuai
    }

    @GetMapping("/add")
    public String createForm(Model model) {
        model.addAttribute("journal", new Journal());
        return "add-journal"; // Ganti dengan nama template untuk form penambahan jurnal
    }

    @PostMapping("/save")
    public String saveJournal(@ModelAttribute Journal journal) {
        journalService.saveJournal(journal);
        return "redirect:/journals"; // Redirect ke daftar jurnal setelah disimpan
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Journal journal = journalService.getJournalById(id).orElse(null);
        model.addAttribute("journal", journal);
        return "edit-journal"; // Ganti dengan nama template untuk form edit jurnal
    }

    @PostMapping("/update/{id}")
    public String updateJournal(@PathVariable Long id, @ModelAttribute Journal journal) {
        journal.setId(id); // Set ID untuk memastikan entitas yang benar diperbarui
        journalService.saveJournal(journal);
        return "redirect:/journals"; // Redirect ke daftar jurnal setelah diperbarui
    }

    @GetMapping("/delete/{id}")
    public String deleteJournal(@PathVariable Long id) {
        journalService.deleteJournal(id);
        return "redirect:/journals"; // Redirect ke daftar jurnal setelah dihapus
    }
}