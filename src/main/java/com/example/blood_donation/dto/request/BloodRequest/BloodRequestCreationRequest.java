package com.example.blood_donation.dto.request.BloodRequest;

import com.example.blood_donation.entity.Account;
import com.example.blood_donation.enumType.BloodGroup;
import com.example.blood_donation.enumType.RequestPriority;
import com.example.blood_donation.validation.EnumValue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class BloodRequestCreationRequest {
    @NotNull
    private Account account;

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
    @FutureOrPresent(message = "End time must be today or in the future")
    private LocalDate endTime;

    private Boolean is_active = Boolean.TRUE;
}
