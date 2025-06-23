package com.example.blood_donation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

}
