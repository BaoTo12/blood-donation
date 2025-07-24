package com.example.blood_donation.dto.requests.donation;

import com.example.blood_donation.enumType.DonationType;
import com.example.blood_donation.validation.EnumValue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DonationCreationRequest {

    @NotNull(message = "Appointment ID is required")
    private Long appointmentId;

    @EnumValue(name = "donationType", enumClass = DonationType.class)
    private String donationType;

    @NotNull(message = "Donation amount is required")
    @Min(value = 1, message = "Donation amount must be positive")
    @Max(value = 1000, message = "Donation amount cannot exceed 1000 ml")
    private Integer amount;
}