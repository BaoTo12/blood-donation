package com.example.blood_donation.enumType;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum BloodGroup {
    @JsonProperty("o+")
    O_PLUS,
    @JsonProperty("o-")
    O_MINUS,
    @JsonProperty("a+")
    A_PLUS,
    @JsonProperty("a-")
    A_MINUS,
    @JsonProperty("b+")
    B_PLUS,
    @JsonProperty("b-")
    B_MINUS,
    @JsonProperty("ab+")
    AB_PLUS,
    @JsonProperty("ab-")
    AB_MINUS
}
