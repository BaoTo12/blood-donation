package com.example.blood_donation.enumType;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum RequestPriority {
    @JsonProperty("low")
    LOW,
    @JsonProperty("medium")
    MEDIUM,
    @JsonProperty("high")
    HIGH
}
