package com.uas.prodcosting.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "journal_entries")
public class JournalEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) // Relasi ke Account
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "type", nullable = false, length = 10)
    private String type;

    @ManyToOne(optional = false) // Relasi ke Company
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne(optional = false) // Relasi ke Journal
    @JoinColumn(name = "journal_id", nullable = false)
    private Journal journal;

    @Column(name = "journal_date", nullable = false)
    private String journalDate;

    @Column(name = "description", nullable = false)
    private String description;
}
