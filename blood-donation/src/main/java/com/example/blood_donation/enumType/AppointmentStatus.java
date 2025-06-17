package com.example.blood_donation.enumType;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AppointmentStatus {
    @JsonProperty("pending")
    PENDING,
    @JsonProperty("approved")
    APPROVED,
    @JsonProperty("completed")
    COMPLETED,
    @JsonProperty("suspended")
    SUSPENDED
}
