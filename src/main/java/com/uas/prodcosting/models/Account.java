package com.uas.prodcosting.models;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_name", nullable = false, length = 100)
    private String accountName;

    @OneToMany(mappedBy = "debitAccount", cascade = CascadeType.ALL)
    private List<Journal> debitJournals = new ArrayList<>();

    @OneToMany(mappedBy = "creditAccount", cascade = CascadeType.ALL)
    private List<Journal> creditJournals = new ArrayList<>();

    // Getters and Setters
}
