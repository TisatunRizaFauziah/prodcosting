package com.uas.prodcosting.repositories;

import com.uas.prodcosting.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    // Sudah include basic CRUD operations dari JpaRepository:
    // - findAll()
    // - findById()
    // - save()
    // - deleteById()
    // dll.
}