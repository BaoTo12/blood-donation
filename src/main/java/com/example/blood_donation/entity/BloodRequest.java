package com.example.blood_donation.entity;


import com.example.blood_donation.enumType.BloodGroup;
import com.example.blood_donation.enumType.RequestPriority;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@Table(name = "blood_request")
@Getter
@Setter
@DynamicUpdate
public class BloodRequest extends BaseEntity {

    @OneToOne
    private Account account;

    private String priority;

    private String bloodGroup;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate startTime;

    @Column(nullable = false)
    private LocalDate endTime;

    @Column(nullable = false)
    private Boolean is_active;


}
