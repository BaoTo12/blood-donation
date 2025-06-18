package com.example.blood_donation.entity;

import jakarta.persistence.*;

@Entity
public class Blog extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

}
