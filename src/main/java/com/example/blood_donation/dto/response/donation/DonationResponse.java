package com.example.blood_donation.dto.response.donation;

import com.example.blood_donation.enumType.DonationType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DonationResponse {

    private Long id;
    private Long appointmentId;
    private DonationType donationType;
    private Integer amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}