package com.example.blood_donation.entity;


import com.example.blood_donation.enumType.BloodGroup;
import com.example.blood_donation.enumType.Gender;
import com.example.blood_donation.enumType.Role;
import com.example.blood_donation.validation.EnumValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
public class Account extends BaseEntity {

    private String role;

    private String email;

    private String password;

    private String phone;

    private String name;

    private String gender;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "blood_group", length = 10)
    private String bloodGroup;

    private LocalDate birthday;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

}
