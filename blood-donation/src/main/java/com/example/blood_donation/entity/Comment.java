package com.example.blood_donation.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Comment extends BaseEntity {

    @ManyToOne
    private Blog blog;

    @ManyToOne
    private Account account;

    private String content;
}
