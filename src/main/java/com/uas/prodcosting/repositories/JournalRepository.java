package com.uas.prodcosting.repositories;

import com.uas.prodcosting.models.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {
    // Anda dapat menambahkan metode kustom di sini jika diperlukan
}