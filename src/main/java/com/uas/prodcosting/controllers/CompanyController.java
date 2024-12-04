package com.uas.prodcosting.controllers;

import com.uas.prodcosting.models.Company;
import com.uas.prodcosting.services.CompanyService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/companies")
   public String listCompanies(Model model) {
       List<Company> companies = companyService.getAllCompanies();
       model.addAttribute("companies", companies);
       return "list-companies";
   }
   @GetMapping("/detail/{id}")
public String getCompanyDetail(@PathVariable Long id, Model model) {
    Company company = companyService.getCompanyById(id);
    model.addAttribute("company", company);
    return "detail-company";
}
    @GetMapping("/ekuivalensi/{id}")
    public String ekuivalensi(@PathVariable Long id,Model model)
    {
        return companyService.unitEkuivalensi(id,model);
    }
    @GetMapping("/hpppbj/{id}")
    public String hppPerunit(@PathVariable Long id,Model model)
    {
        return companyService.hppPerunitBj(id,model);
    }
    @GetMapping("/hppj/{id}")
    public String hppJadiBdp(@PathVariable Long id,Model model)
    {
        return companyService.hppJadi(id,model);
    }

    @GetMapping("/laporan/{id}")
    public String laporan(@PathVariable Long id,Model model)
    {
        return companyService.Laporan(id,model);
    }
    @GetMapping("/add-companies")
    public String createForm(Model model) {
        model.addAttribute("company", new Company());
        return "add-companies";
    }

    @PostMapping("/save")
    public String saveCompany(@ModelAttribute Company company) {
        companyService.saveCompany(company);
        return "redirect:/companies";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Company company = companyService.getCompanyById(id);
        model.addAttribute("company", company);
        return "update-companies";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return "redirect:/companies";
    }
      // Proses edit data
      @PostMapping("/update")
      public String updateCompany(@ModelAttribute Company company) {
          System.out.println("Updating company with ID: " + company.getId());
  
          // Hitung ulang total biaya produksi
          double totalBiaya = company.getBiayaBahanBaku()
                  + company.getBiayaBahanPenolong()
                  + company.getBiayaTenagaKerja()
                  + company.getBop();
          company.setTotalBiayaProduksi(totalBiaya);
  
          // Update data perusahaan
          companyService.saveCompany(company);
  
          return "redirect:/companies";
      }

   
}
