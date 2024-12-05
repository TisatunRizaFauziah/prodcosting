package com.uas.prodcosting.models;

import java.util.ArrayList;
import java.util.List;

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

    private Double biayaBahanBaku = 0.0;
    private Double biayaBahanPenolong = 0.0;
    private Double biayaTenagaKerja = 0.0;
    private Double bop = 0.0;
    private Double totalBiayaProduksi = 0.0;

    private long produkJadi ;
    private long produkDalamProses ;
    private Double persentaseBahanBaku;
    private Double persentaseBahanPenolong ;
    private Double persentaseTenagaKerja ;
    private Double persentaseBop ;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Journal> journals = new ArrayList<>();

}
