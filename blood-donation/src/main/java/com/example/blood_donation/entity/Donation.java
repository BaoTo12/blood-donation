package com.example.blood_donation.entity;


import com.example.blood_donation.enumType.DonationType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;

@Entity
public class Donation extends BaseEntity{

    @OneToOne
    private Appointment appointment;

    @Enumerated(EnumType.STRING)
    private DonationType donationType;

    private Integer amount;
}
