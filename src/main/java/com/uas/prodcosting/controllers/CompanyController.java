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

    // @PostMapping("/save")
    // public String saveCompany(@ModelAttribute Company company) {
    //     companyService.saveCompany(company);
    //     return "redirect:/companies";
    // }

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
    //   @PostMapping("/update")
    //   public String updateCompany(@ModelAttribute Company company) {
    //       System.out.println("Updating company with ID: " + company.getId());
  
    //       // Hitung ulang total biaya produksi
    //       double totalBiaya = company.getBiayaBahanBaku()
    //               + company.getBiayaBahanPenolong()
    //               + company.getBiayaTenagaKerja()
    //               + company.getBop();
    //       company.setTotalBiayaProduksi(totalBiaya);
  
    //       // Update data perusahaan
    //       companyService.saveCompany(company);
  
    //       return "redirect:/companies";
    //   }
      @GetMapping("/search")
      public String cari(@RequestParam(value ="name")String name,Model model)
      {
        List<Company> company = companyService.findByName(name);
        model.addAttribute("companies",company);
        return "list-companies";
      }
      @GetMapping("/sortAsc")
      public String sort(Model model)
      {
        List<Company> company = companyService.findByNameSort();
        model.addAttribute("companies",company);
        return "list-companies";
      }


    // Validasi dan simpan perusahaan baru
    @PostMapping("/save")
    public String saveCompany(@ModelAttribute("company") Company company, Model model) {
        // Validasi input
        boolean hasError = validateCompany(company, model);

        // Jika ada error, kembali ke halaman tambah perusahaan
        if (hasError) {
            return "add-companies";
        }

        // Simpan perusahaan ke database
        companyService.saveCompany(company);
        return "redirect:/companies";
    }

    // Form untuk memperbarui perusahaan
    @PostMapping("/update")
    public String updateCompany(@ModelAttribute("company") Company company, Model model) {
        // Validasi input
        boolean hasError = validateCompany(company, model);

        // Jika ada error, kembali ke halaman edit perusahaan
        if (hasError) {
            model.addAttribute("company", company);
            return "edit-company";
        }

        // Hitung ulang total biaya produksi
        double totalBiaya = company.getBiayaBahanBaku()
                + company.getBiayaBahanPenolong()
                + company.getBiayaTenagaKerja()
                + company.getBop();
        company.setTotalBiayaProduksi(totalBiaya);

        // Update data perusahaan di database
        companyService.saveCompany(company);

        return "redirect:/companies";
    }

    /**
     * Validasi untuk data perusahaan.
     */
    private boolean validateCompany(Company company, Model model) {
        boolean hasError = false;

        // Validasi nama perusahaan
        if (company.getName() == null || company.getName().isBlank()) {
            model.addAttribute("nameError", "Nama perusahaan tidak boleh kosong.");
            hasError = true;
        } else if (company.getName().length() < 3 || company.getName().length() > 50) {
            model.addAttribute("nameError", "Nama perusahaan harus antara 3 hingga 50 karakter.");
            hasError = true;
        }

        // Validasi deskripsi perusahaan
        if (company.getDescription() == null || company.getDescription().isBlank()) {
            model.addAttribute("descriptionError", "Deskripsi tidak boleh kosong.");
            hasError = true;
        }
         
        // Validasi Priode (format: Januari 2024)
    String priodePattern = "^(Januari|Februari|Maret|April|Mei|Juni|Juli|Agustus|September|Oktober|November|Desember)\\s\\d{4}$";
    if (company.getPriode() == null || company.getPriode().isBlank()) {
        model.addAttribute("priodeError", "Periode tidak boleh kosong.");
        hasError = true;
    } else if (!company.getPriode().matches(priodePattern)) {
        model.addAttribute("priodeError", "Periode harus dalam format 'Januari 2024'.");
        hasError = true;
    }

        // Validasi biaya bahan baku
        if (company.getBiayaBahanBaku() == null || company.getBiayaBahanBaku() < 0) {
            model.addAttribute("biayaBahanBakuError", "Biaya bahan baku tidak boleh negatif.");
            hasError = true;
        }

        // Validasi biaya tenaga kerja
        if (company.getBiayaTenagaKerja() == null || company.getBiayaTenagaKerja() < 0) {
            model.addAttribute("biayaTenagaKerjaError", "Biaya tenaga kerja tidak boleh negatif.");
            hasError = true;
        }
         // Validasi biaya tenaga kerja
         if (company.getBiayaBahanPenolong() == null || company.getBiayaBahanPenolong() < 0) {
            model.addAttribute("biayaBahanPenolongError", "Biaya Bahan Penolong tidak boleh negatif.");
            hasError = true;
        }
        if (company.getBop() == null || company.getBop() < 0) {
            model.addAttribute("biayaBopError", "Biaya bop tidak boleh negatif.");
            hasError = true;
        }




        return hasError;
    }
    @GetMapping("/about")
    public String aboutPage() {
        return "about"; // Mengembalikan nama file HTML untuk halaman About
    }
   
}
