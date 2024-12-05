package com.uas.prodcosting.repositories;

import com.uas.prodcosting.models.Company;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    List <Company> findByNameContainingIgnoreCase(String name);
    List <Company> findAllByOrderByNameAsc();
}