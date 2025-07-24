package com.example.blood_donation.dto.requests.appointment;

import com.example.blood_donation.enumType.AppointmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AppointmentUpdateRequest {

    @NotNull
    private AppointmentStatus status;
}
