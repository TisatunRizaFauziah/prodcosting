package com.uas.prodcosting.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.uas.prodcosting.models.Account;
import com.uas.prodcosting.models.Company;
import com.uas.prodcosting.models.Journal;
import com.uas.prodcosting.repositories.AccountRepository;
import com.uas.prodcosting.repositories.CompanyRepository;
import com.uas.prodcosting.repositories.JournalRepository;

@Service
public class JournalService {
    private final JournalRepository journalRepository;
    private final CompanyRepository companyRepository;
    private final AccountRepository accountRepository;

    public JournalService(JournalRepository journalRepository, CompanyRepository companyRepository, AccountRepository accountRepository) {
        this.journalRepository = journalRepository;
        this.companyRepository = companyRepository;
        this.accountRepository = accountRepository;
    }

    // CREATE
    public Journal createJournal(Long companyId, Long debitAccountId, Long creditAccountId, Journal journal) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new RuntimeException("Company not found"));
        Account debitAccount = accountRepository.findById(debitAccountId).orElseThrow(() -> new RuntimeException("Debit Account not found"));
        Account creditAccount = accountRepository.findById(creditAccountId).orElseThrow(() -> new RuntimeException("Credit Account not found"));

        journal.setCompany(company);
        journal.setDebitAccount(debitAccount);
        journal.setCreditAccount(creditAccount);

        return journalRepository.save(journal);
    }

    // READ ALL
    public List<Journal> getAllJournals() {
        return journalRepository.findAll();
    }

    // READ BY ID
    public Journal getJournalById(Long id) {
        return journalRepository.findById(id).orElseThrow(() -> new RuntimeException("Journal not found"));
    }

    // READ BY COMPANY ID
    public List<Journal> getJournalsByCompanyId(Long companyId) {
        return journalRepository.findByCompanyId(companyId);
    }

    // UPDATE
    public Journal updateJournal(Long id, Long companyId, Long debitAccountId, Long creditAccountId, Journal updatedJournal) {
        Journal journal = getJournalById(id); // Fetch existing journal
    
        // Fetch related entities
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        Account debitAccount = accountRepository.findById(debitAccountId)
                .orElseThrow(() -> new RuntimeException("Debit Account not found"));
        Account creditAccount = accountRepository.findById(creditAccountId)
                .orElseThrow(() -> new RuntimeException("Credit Account not found"));
    
        // Update fields
        journal.setCompany(company);
        journal.setDebitAccount(debitAccount);
        journal.setCreditAccount(creditAccount);
        journal.setTransactionDate(updatedJournal.getTransactionDate());
        journal.setDescription(updatedJournal.getDescription());
        journal.setDebitAmount(updatedJournal.getDebitAmount());
        journal.setCreditAmount(updatedJournal.getCreditAmount());
    
        return journalRepository.save(journal);
    }
    

    public void saveJournal(Journal journal) {
        journalRepository.save(journal);
    }
    // DELETE
    public void deleteJournal(Long id) {
        Journal journal = getJournalById(id);
        journalRepository.delete(journal);
    }
}
