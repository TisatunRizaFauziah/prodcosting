package com.uas.prodcosting.services;

import com.uas.prodcosting.models.Company;
import com.uas.prodcosting.repositories.CompanyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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

    public String unitEkuivalensi(Long id, Model model) {
        
        long biayaBahanBaku;
        long biayaBahanPenolong;
        long biayaTenagaKerja;
        long biayaOverhedPabrik;

        Company company = companyRepository.findById(id).orElse(null);
    
        if (company != null) {
            // Konversi persentase ke bentuk desimal
            double persentaseBahanBaku = company.getPersentaseBahanBaku() / 100.0;
            double persentaseBahanPenolong = company.getPersentaseBahanPenolong() / 100.0;
            double persentaseTenagaKerja = company.getPersentaseTenagaKerja() / 100.0;
            double persentaseBop = company.getPersentaseBop() / 100.0;
    
            biayaBahanBaku = company.getProdukJadi() + Math.round(company.getProdukDalamProses() * persentaseBahanBaku);
            biayaBahanPenolong = company.getProdukJadi() + Math.round(company.getProdukDalamProses() * persentaseBahanPenolong);
            biayaTenagaKerja = company.getProdukJadi() + Math.round(company.getProdukDalamProses() * persentaseTenagaKerja);
            biayaOverhedPabrik = company.getProdukJadi() + Math.round(company.getProdukDalamProses() * persentaseBop);
    
            model.addAttribute("company", company);
            model.addAttribute("bbb", biayaBahanBaku);
            model.addAttribute("bbp", biayaBahanPenolong);
            model.addAttribute("btk", biayaTenagaKerja);
            model.addAttribute("bop", biayaOverhedPabrik);
        } else {
            model.addAttribute("error", "Company data not found.");
        }
        
        return "unit-ekuivalensi";
    }

    public String hppPerunitBj(long id,Model model)
    {
        Company company = companyRepository.findById(id).orElse(null);
         
        double persentaseBahanBaku = company.getPersentaseBahanBaku() / 100.0;
        double persentaseBahanPenolong = company.getPersentaseBahanPenolong() / 100.0;
        double persentaseTenagaKerja = company.getPersentaseTenagaKerja() / 100.0;
        double persentaseBop = company.getPersentaseBop() / 100.0;

        long biayaBahanBaku = company.getProdukJadi() + Math.round(company.getProdukDalamProses() * persentaseBahanBaku);
        long biayaBahanPenolong = company.getProdukJadi() + Math.round(company.getProdukDalamProses() * persentaseBahanPenolong);
        long biayaTenagaKerja = company.getProdukJadi() + Math.round(company.getProdukDalamProses() * persentaseTenagaKerja);
        long bop = company.getProdukJadi() + Math.round(company.getProdukDalamProses() * persentaseBop);

        long totalbbb=company.getBiayaBahanBaku()/biayaBahanBaku;
        long totalbbp=company.getBiayaBahanPenolong()/biayaBahanPenolong;
        long totalbtk=company.getBiayaTenagaKerja()/biayaTenagaKerja;
        long totalbop=company.getBop()/bop;

        model.addAttribute("company", company);
        model.addAttribute("tbbb", totalbbb);
        model.addAttribute("tbbp", totalbbp);
        model.addAttribute("tbtk", totalbtk);
        model.addAttribute("tbop", totalbop);
        model.addAttribute("bbb", biayaBahanBaku);
        model.addAttribute("bbp", biayaBahanPenolong);
        model.addAttribute("btk", biayaTenagaKerja);
        model.addAttribute("bop", bop);
        model.addAttribute("totalBiaya", company.getBiayaBahanBaku()+company.getBiayaBahanPenolong()+company.getBiayaTenagaKerja()+company.getBop());
        model.addAttribute("totalBiayaPerUnit", totalbbb+totalbbp+totalbtk+totalbop);
        return "hpp-perunit-bj";
    }

    public String hppJadi(Long id, Model model) {

        Company company = companyRepository.findById(id).orElse(null);
        if (company == null) {
            model.addAttribute("error", "Company not found");
            return "error-page"; // Halaman error
        }
    
        try {
            double persentaseBahanBaku = company.getPersentaseBahanBaku() / 100.0;
            double persentaseBahanPenolong = company.getPersentaseBahanPenolong() / 100.0;
            double persentaseTenagaKerja = company.getPersentaseTenagaKerja() / 100.0;
            double persentaseBop = company.getPersentaseBop() / 100.0;
    
            long biayaBahanBaku = Math.round(company.getProdukJadi() + (company.getProdukDalamProses() * persentaseBahanBaku));
            long biayaBahanPenolong = Math.round(company.getProdukJadi() + (company.getProdukDalamProses() * persentaseBahanPenolong));
            long biayaTenagaKerja = Math.round(company.getProdukJadi() + (company.getProdukDalamProses() * persentaseTenagaKerja));
            long bop = Math.round(company.getProdukJadi() + (company.getProdukDalamProses() * persentaseBop));
    
            long totalbbb = company.getBiayaBahanBaku() / biayaBahanBaku;
            long totalbbp = company.getBiayaBahanPenolong() / biayaBahanPenolong;
            long totalbtk = company.getBiayaTenagaKerja() / biayaTenagaKerja;
            long totalbop = company.getBop() /bop;
    
            long tbProduksiPerunit = totalbbb + totalbbp + totalbtk + totalbop;
            long tProdukJadi = company.getProdukJadi() * tbProduksiPerunit;
    
            long bbb = Math.round(persentaseBahanBaku * company.getProdukDalamProses() * totalbbb);
            long bbp = Math.round(persentaseBahanPenolong * company.getProdukDalamProses() * totalbbp);
            long btk = Math.round(persentaseTenagaKerja * company.getProdukDalamProses() * totalbtk);
            long bbop = Math.round(persentaseBop * company.getProdukDalamProses() * totalbop);
    
            long tBarangDalamProses = bbb + bbp + btk + bbop;
    
            model.addAttribute("company", company);
            model.addAttribute("tbProduksiPerunit", tbProduksiPerunit);
            model.addAttribute("tProdukJadi", tProdukJadi);
            model.addAttribute("bbb", bbb);
            model.addAttribute("bbp", bbp);
            model.addAttribute("btk", btk);
            model.addAttribute("bop", bbop);
            model.addAttribute("tBarangDalamProses", tBarangDalamProses);
            model.addAttribute("total", tProdukJadi + tBarangDalamProses);
    
            return "hppj-bdp";
        } catch (Exception e) {
            model.addAttribute("error", "Error calculating HPP: " + e.getMessage());
            return "error-page";
        }
    }
    
    
}
