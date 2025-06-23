package com.example.blood_donation.entity;


import com.example.blood_donation.enumType.BloodGroup;
import com.example.blood_donation.enumType.Gender;
import com.example.blood_donation.validation.EnumValue;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@DynamicUpdate
public class Account extends BaseEntity {


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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns =  @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

}
