package com.example.blood_donation.dto.request.BloodRequest;

import com.example.blood_donation.validation.DataValidRange;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@DataValidRange(
        startFieldName = "startTime",
        endFieldName = "endTime",
        message = "Blood request start time must be before or equal to end time"
)
public class BloodRequestUpdateRequest {
    private String priority;

    private String bloodGroup;

    private String title;

    @FutureOrPresent(message = "Start time must be today or in the future")
    private LocalDate startTime;

    @Future(message = "End time must be today or in the future")
    private LocalDate endTime;
}
