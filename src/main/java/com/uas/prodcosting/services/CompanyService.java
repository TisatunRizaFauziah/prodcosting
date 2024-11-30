package com.uas.prodcosting.services;

import com.uas.prodcosting.models.Company;
import com.uas.prodcosting.repositories.CompanyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    
    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company saveCompany(Company company) {
        // Hitung biaya berdasarkan persentase penyelesaian
        Long biayaBahanBakuSelesai = company.getBiayaBahanBaku();
        Long biayaBahanPenolongSelesai = company.getBiayaBahanPenolong();
        Long biayaTenagaKerjaSelesai = company.getBiayaTenagaKerja();
        Long bopSelesai = company.getBop();

        // Hitung total biaya produksi
        Long totalBiaya = biayaBahanBakuSelesai + 
                          biayaBahanPenolongSelesai + 
                          biayaTenagaKerjaSelesai + 
                          bopSelesai;

        company.setTotalBiayaProduksi(totalBiaya);
        return companyRepository.save(company);
    }

    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
}
