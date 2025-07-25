package com.example.blood_donation.dto.requests.health;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class HealthCreationRequest {

    @NotNull(message = "Appointment ID is required")
    private Long appointmentId;

    @DecimalMin(value = "30.0", message = "Temperature must be at least 30.0°C")
    @DecimalMax(value = "45.0", message = "Temperature must not exceed 45.0°C")
    private Float temperature;

    @DecimalMin(value = "1.0", message = "Weight must be positive")
    @DecimalMax(value = "500.0", message = "Weight must be realistic")
    private Float weight;

    @Min(value = 30, message = "Heart pulse must be at least 30 bpm")
    @Max(value = 250, message = "Heart pulse must not exceed 250 bpm")
    private Integer heartPulse;

    @NotNull(message = "Health status is required")
    private Boolean isGoodHealth;

    @Size(max = 1000, message = "Note must not exceed 1000 characters")
    private String note;
}