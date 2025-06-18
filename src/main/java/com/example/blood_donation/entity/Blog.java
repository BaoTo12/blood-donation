package com.example.blood_donation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Blog extends BaseEntity{

    @ManyToOne()
    private Account account;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

}
