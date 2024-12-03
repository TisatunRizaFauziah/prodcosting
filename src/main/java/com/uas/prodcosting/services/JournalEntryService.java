package com.uas.prodcosting.services;

import com.uas.prodcosting.models.JournalEntry;
import com.uas.prodcosting.repositories.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public List<JournalEntry> getAllJournalEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalEntryById(Long id) {
        return journalEntryRepository.findById(id);
    }

    public void saveJournalEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public void deleteJournalEntry(Long id) {
        journalEntryRepository.deleteById(id);
    }
}