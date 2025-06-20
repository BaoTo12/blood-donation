package com.example.blood_donation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@ToString(exclude = "appointment") // Prevent circular reference in toString
@DynamicUpdate
@Getter
@Setter
public class Health extends BaseEntity{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", unique = true)
    private Appointment appointment;

    @Column(columnDefinition = "DECIMAL(4,1)")
    private Float temperature;

    @Column(columnDefinition = "DECIMAL(5,2)")
    private Float weight;

    @Column(name = "heart_pulse")
    private Integer heartPulse;

    @Column(name = "is_good_health", nullable = false)
    private Boolean isGoodHealth = false;

    @Column(columnDefinition = "TEXT")
    private String note;

}
