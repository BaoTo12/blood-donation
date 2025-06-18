package com.example.blood_donation.enumType;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    MEMBER("member"),
    STAFF("staff"),
    ADMIN("admin");

    private final String jsonValue;

    @JsonValue
    @Override
    public String toString() {
        return jsonValue;
    }


    Role(String jsonValue){
        this.jsonValue = jsonValue;
    }

}
