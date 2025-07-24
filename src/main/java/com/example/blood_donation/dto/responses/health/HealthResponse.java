package com.example.blood_donation.dto.response.health;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class HealthResponse {

    private Long id;
    private Long appointmentId;
    private Float temperature;
    private Float weight;
    private Integer heartPulse;
    private Boolean isGoodHealth;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}