package com.uas.prodcosting.repositories;

import com.uas.prodcosting.models.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {
    // Anda dapat menambahkan metode kustom di sini jika diperlukan
}