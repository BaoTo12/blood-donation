package com.example.blood_donation.dto.responses.BloodRequest;

import com.example.blood_donation.dto.responses.account.AccountResponse;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BloodRequestResponse {
    private AccountResponse account;

    private String priority;

    private String bloodGroup;

    private String title;

    private LocalDate startTime;

    private LocalDate endTime;

    private LocalDate created_at;
}
