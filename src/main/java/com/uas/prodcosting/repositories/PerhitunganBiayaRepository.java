package com.uas.prodcosting.repositories;

import com.uas.prodcosting.models.PerhitunganBiaya;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerhitunganBiayaRepository extends JpaRepository<PerhitunganBiaya, Long> {
    PerhitunganBiaya findByCompanyId(Long companyId);
}
