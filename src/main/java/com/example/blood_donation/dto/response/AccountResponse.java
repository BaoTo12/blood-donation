package com.example.blood_donation.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class AccountResponse {
    private String role;

    private String email;

    private String phone;

    private String name;

    private String gender;

    private String address;

    private String bloodGroup;

    private LocalDate birthday;

    private boolean active = true;
}
