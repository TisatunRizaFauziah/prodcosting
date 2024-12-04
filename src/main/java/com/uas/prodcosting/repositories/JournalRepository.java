package com.uas.prodcosting.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uas.prodcosting.models.Journal;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {
    List<Journal> findByCompanyId(Long companyId);

    @Query("SELECT j FROM Journal j " +
           "JOIN FETCH j.company c " +
           "JOIN FETCH j.debitAccount da " +
           "JOIN FETCH j.creditAccount ca " +
           "WHERE c.id = :companyId")
    List<Journal> findJournalsByCompanyId(@Param("companyId") Long companyId);
}
