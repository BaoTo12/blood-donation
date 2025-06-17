package com.example.blood_donation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;

@Entity
public class Health extends BaseEntity{

    @OneToOne(fetch = FetchType.EAGER)
    private Appointment appointment;

    private Float temperature;

    private Float weight;

    @Column(name = "heart_pulse")
    private Integer heartPulse;

    @Column(name = "is_good_health")
    private Boolean isGoodHealth;

    @Column(columnDefinition = "TEXT")
    private String note;

}
