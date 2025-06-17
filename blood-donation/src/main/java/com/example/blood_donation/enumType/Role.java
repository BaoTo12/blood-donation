package com.example.blood_donation.enumType;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Role {
    @JsonProperty("member")
    MEMBER,
    @JsonProperty("staff")
    STAFF,
    @JsonProperty("admin")
    ADMIN
}
