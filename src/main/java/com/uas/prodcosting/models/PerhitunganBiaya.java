package com.uas.prodcosting.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;
import java.time.LocalDateTime;


@Entity
@Data
public class PerhitunganBiaya {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "company_id")
    private Company company;

    // Unit Ekuivalensi (Tahap 1)
    private Long unitEkuivalensiBB;
    private Long unitEkuivalensiBP;
    private Long unitEkuivalensiTK;
    private Long unitEkuivalensiBOP;

    // Biaya Per Unit (Tahap 2)
    private Long biayaPerUnitBB;
    private Long biayaPerUnitBP;
    private Long biayaPerUnitTK;
    private Long biayaPerUnitBOP;
    private Long totalBiayaPerUnit;

    // Timestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}