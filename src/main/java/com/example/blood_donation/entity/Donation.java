package com.example.blood_donation.entity;


import com.example.blood_donation.enumType.DonationType;
import com.example.blood_donation.validation.EnumValue;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@ToString(exclude = "appointment")
@DynamicUpdate
public class Donation extends BaseEntity{

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", unique = true, nullable = false)
    private Appointment appointment;

    @EnumValue(name = "donationType", enumClass = DonationType.class)
    private String donationType;

    @Column(nullable = false)
    private Integer amount;
}
