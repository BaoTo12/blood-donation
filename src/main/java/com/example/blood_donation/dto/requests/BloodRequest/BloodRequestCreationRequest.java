package com.example.blood_donation.dto.requests.BloodRequest;

import com.example.blood_donation.enumType.BloodGroup;
import com.example.blood_donation.enumType.RequestPriority;
import com.example.blood_donation.validation.DataValidRange;
import com.example.blood_donation.validation.EnumValue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
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
public class BloodRequestCreationRequest {
    @NotNull
    private Long account_id;

    @EnumValue(name = "priority", enumClass = RequestPriority.class)
    @NotNull
    private String priority;

    @EnumValue(name = "bloodGroup", enumClass = BloodGroup.class)
    private String bloodGroup;

    @NotNull
    private String title;

    @NotNull
    @FutureOrPresent(message = "Start time must be today or in the future")
    private LocalDate startTime;

    @NotNull
    @Future(message = "End time must be today or in the future")
    private LocalDate endTime;

    private Boolean is_active = Boolean.TRUE;
}
