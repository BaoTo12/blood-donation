package com.example.blood_donation.dto.request.donation;

import com.example.blood_donation.enumType.DonationType;
import com.example.blood_donation.validation.EnumValue;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DonationUpdateRequest {

    private Long appointmentId;

    @EnumValue(name = "donationType", enumClass = DonationType.class)
    private String donationType;

    @Min(value = 1, message = "Donation amount must be positive")
    @Max(value = 1000, message = "Donation amount cannot exceed 1000 ml")
    private Integer amount;
}