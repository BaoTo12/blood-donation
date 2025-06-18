package com.example.blood_donation.enumType;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public enum BloodGroup {
    O_PLUS("o+"),
    O_MINUS("o-"),
    A_PLUS("a+"),
    A_MINUS("a-"),
    B_PLUS("b+"),
    B_MINUS("b-"),
    AB_PLUS("ab-"),
    AB_MINUS("ab-");

    private final String jsonValue;

    BloodGroup(String jsonValue) {
        this.jsonValue = jsonValue;
    }

    @JsonValue
    @Override
    public String toString() {
        return jsonValue;
    }

}
