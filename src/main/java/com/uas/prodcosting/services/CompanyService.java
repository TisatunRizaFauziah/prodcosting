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
        double biayaBahanBakuSelesai = company.getBiayaBahanBaku();
        double biayaBahanPenolongSelesai = company.getBiayaBahanPenolong();
        double biayaTenagaKerjaSelesai = company.getBiayaTenagaKerja();
        double bopSelesai = company.getBop();

        // Hitung total biaya produksi
        double totalBiaya = biayaBahanBakuSelesai + 
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
        
        double biayaBahanBaku;
        double biayaBahanPenolong;
        double biayaTenagaKerja;
        double biayaOverhedPabrik;

        Company company = companyRepository.findById(id).orElse(null);
    
        if (company != null) {
            // Konversi persentase ke bentuk desimal
            double persentaseBahanBaku = company.getPersentaseBahanBaku() / 100.0;
            double persentaseBahanPenolong = company.getPersentaseBahanPenolong() / 100.0;
            double persentaseTenagaKerja = company.getPersentaseTenagaKerja() / 100.0;
            double persentaseBop = company.getPersentaseBop() / 100.0;
    
            biayaBahanBaku = company.getProdukJadi() + (company.getProdukDalamProses() * persentaseBahanBaku);
            biayaBahanPenolong = company.getProdukJadi() + (company.getProdukDalamProses() * persentaseBahanPenolong);
            biayaTenagaKerja = company.getProdukJadi() + (company.getProdukDalamProses() * persentaseTenagaKerja);
            biayaOverhedPabrik = company.getProdukJadi() + (company.getProdukDalamProses() * persentaseBop);
    
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
    public String hppPerunitBj(Long id, Model model) {
        Company company = companyRepository.findById(id).orElse(null);
    
        if (company != null) {
            double persentaseBahanBaku = company.getPersentaseBahanBaku() / 100.0;
            double persentaseBahanPenolong = company.getPersentaseBahanPenolong() / 100.0;
            double persentaseTenagaKerja = company.getPersentaseTenagaKerja() / 100.0;
            double persentaseBop = company.getPersentaseBop() / 100.0;
    
            double biayaBahanBaku = company.getProdukJadi() + (company.getProdukDalamProses() * persentaseBahanBaku);
            double biayaBahanPenolong = company.getProdukJadi() + (company.getProdukDalamProses() * persentaseBahanPenolong);
            double biayaTenagaKerja = company.getProdukJadi() + (company.getProdukDalamProses() * persentaseTenagaKerja);
            double bop = company.getProdukJadi() + (company.getProdukDalamProses() * persentaseBop);
    
            double totalbbb = company.getBiayaBahanBaku() / biayaBahanBaku;
            double totalbbp = company.getBiayaBahanPenolong() / biayaBahanPenolong;
            double totalbtk = company.getBiayaTenagaKerja() / biayaTenagaKerja;
            double totalbop = company.getBop() / bop;
    
            // Membulatkan nilai ke dua desimal
            totalbbb = Math.round(totalbbb * 100.0) / 100.0;
            totalbbp = Math.round(totalbbp * 100.0) / 100.0;
            totalbtk = Math.round(totalbtk * 100.0) / 100.0;
            totalbop = Math.round(totalbop * 100.0) / 100.0;
            biayaBahanBaku = Math.round(biayaBahanBaku * 100.0) / 100.0;
            biayaBahanPenolong = Math.round(biayaBahanPenolong * 100.0) / 100.0;
            biayaTenagaKerja = Math.round(biayaTenagaKerja * 100.0) / 100.0;
            bop = Math.round(bop * 100.0) / 100.0;
    
            model.addAttribute("company", company);
            model.addAttribute("tbbb", totalbbb);
            model.addAttribute("tbbp", totalbbp);
            model.addAttribute("tbtk", totalbtk);
            model.addAttribute("tbop", totalbop);
            model.addAttribute("bbb", biayaBahanBaku);
            model.addAttribute("bbp", biayaBahanPenolong);
            model.addAttribute("btk", biayaTenagaKerja);
            model.addAttribute("bop", bop);
    
            double totalBiaya = company.getBiayaBahanBaku() + company.getBiayaBahanPenolong() +
                    company.getBiayaTenagaKerja() + company.getBop();
            totalBiaya = Math.round(totalBiaya * 100.0) / 100.0;
    
            double totalBiayaPerUnit = totalbbb + totalbbp + totalbtk + totalbop;
            totalBiayaPerUnit = Math.round(totalBiayaPerUnit * 100.0) / 100.0;
    
            model.addAttribute("totalBiaya", totalBiaya);
            model.addAttribute("totalBiayaPerUnit", totalBiayaPerUnit);
        } else {
            model.addAttribute("error", "Company data not found.");
        }
    
        return "hpp-perunit-bj";
    }
    

    public String hppJadi(Long id, Model model) {
        Company company = companyRepository.findById(id).orElse(null);
    
        if (company != null) {
            double persentaseBBB = company.getPersentaseBahanBaku() / 100.0;
            double persentaseBBP = company.getPersentaseBahanPenolong() / 100.0;
            double persentaseBTK = company.getPersentaseTenagaKerja() / 100.0;
            double persentaseBOP = company.getPersentaseBop() / 100.0;
    
            double biayaBB = company.getProdukJadi() + (company.getProdukDalamProses() * persentaseBBB);
            double biayaBP = company.getProdukJadi() + (company.getProdukDalamProses() * persentaseBBP);
            double biayaTK = company.getProdukJadi() + (company.getProdukDalamProses() * persentaseBTK);
            double biayaOP = company.getProdukJadi() + (company.getProdukDalamProses() * persentaseBOP);
    
            double totalBBB = company.getBiayaBahanBaku() / biayaBB;
            double totalBBP = company.getBiayaBahanPenolong() / biayaBP;
            double totalBTK = company.getBiayaTenagaKerja() / biayaTK;
            double totalBOP = company.getBop() / biayaOP;
    
            double totalProduksiPerUnit = totalBBB + totalBBP + totalBTK + totalBOP;
            double totalProdukJadi = company.getProdukJadi() * totalProduksiPerUnit;
    
            double biayaBBB = (persentaseBBB * company.getProdukDalamProses()) * totalBBB;
            double biayaBBP = (persentaseBBP * company.getProdukDalamProses()) * totalBBP;
            double biayaBTK = (persentaseBTK * company.getProdukDalamProses()) * totalBTK;
            double biayaBOP = (persentaseBOP * company.getProdukDalamProses()) * totalBOP;
    
            double totalBarangDalamProses = biayaBBB + biayaBBP + biayaBTK + biayaBOP;
    
            // Membulatkan hasil perhitungan
            long roundedTotalProduksiPerUnit = Math.round(totalProduksiPerUnit);
            long roundedTotalProdukJadi = Math.round(totalProdukJadi);
            long roundedBiayaBBB = Math.round(biayaBBB);
            long roundedBiayaBBP = Math.round(biayaBBP);
            long roundedBiayaBTK = Math.round(biayaBTK);
            long roundedBiayaBOP = Math.round(biayaBOP);
            long roundedTotalBarangDalamProses = Math.round(totalBarangDalamProses);
            long roundedTotal = Math.round(totalProdukJadi + totalBarangDalamProses);
    
            // Menambahkan ke model
            model.addAttribute("company", company);
            model.addAttribute("hppJadi_totalProduksiPerUnit", roundedTotalProduksiPerUnit);
            model.addAttribute("hppJadi_totalProdukJadi", roundedTotalProdukJadi);
            model.addAttribute("hppJadi_biayaBBB", roundedBiayaBBB);
            model.addAttribute("hppJadi_biayaBBP", roundedBiayaBBP);
            model.addAttribute("hppJadi_biayaBTK", roundedBiayaBTK);
            model.addAttribute("hppJadi_biayaBOP", roundedBiayaBOP);
            model.addAttribute("hppJadi_totalBarangDalamProses", roundedTotalBarangDalamProses);
            model.addAttribute("hppJadi_total", roundedTotal);
        } else {
            model.addAttribute("error", "Company data not found.");
        }
    
        return "hppj-bdp";
    }
    
}
