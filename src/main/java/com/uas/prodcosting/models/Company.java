package com.uas.prodcosting.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description; 
    private String priode;

    private Long biayaBahanBaku = 0L;
    private Long biayaBahanPenolong = 0L;
    private Long biayaTenagaKerja = 0L;
    private Long bop = 0L;
    private Long totalBiayaProduksi = 0L;

    private Integer produkJadi = 0;
    private Integer produkDalamProses = 0;
    private Integer persentaseBahanBaku = 0;
    private Integer persentaseBahanPenolong = 0;
    private Integer persentaseTenagaKerja = 0;
    private Integer persentaseBop = 0;

    public Company() {
    }

    public Company(String name, String priode) {
        this.name = name;
        this.priode = priode;
    }
}
