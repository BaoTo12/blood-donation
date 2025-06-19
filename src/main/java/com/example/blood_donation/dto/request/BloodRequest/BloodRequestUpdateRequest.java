package com.example.blood_donation.dto.request.BloodRequest;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class BloodRequestUpdateRequest {
    private String priority;

    private String bloodGroup;

    private String title;

    @FutureOrPresent(message = "Start time must be today or in the future")
    private LocalDate startTime;

    @Future(message = "End time must be today or in the future")
    private LocalDate endTime;
}
