package com.example.blood_donation.enumType;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum DonationType {
    @JsonProperty("whole_blood")
    WHOLE_BLOOD,
    @JsonProperty("power_red")
    POWER_RED,
    @JsonProperty("platelet")
    PLATELET,
    @JsonProperty("plasma")
    PLASMA
}
