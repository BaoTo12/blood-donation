package com.example.blood_donation.dto.requests.appointment;

import com.example.blood_donation.enumType.AppointmentStatus;
import com.example.blood_donation.validation.EnumValue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AppointmentCreationRequest {

    @NotNull
    private Long accountId;

    @NotNull
    private Long requestId;

    @EnumValue(name = "status", enumClass = AppointmentStatus.class)
    private String status;
}
