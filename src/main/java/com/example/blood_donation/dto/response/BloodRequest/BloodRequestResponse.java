package com.example.blood_donation.dto.response.BloodRequest;

import com.example.blood_donation.entity.Account;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BloodRequestResponse {
    private Account account;

    private String priority;

    private String bloodGroup;

    private String title;

    private LocalDate startTime;

    private LocalDate endTime;

    private LocalDate created_at;
}
