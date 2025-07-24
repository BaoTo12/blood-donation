package com.example.blood_donation.dto.responses.account;

import com.example.blood_donation.entity.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
public class AccountResponse {

    private String email;

    private String phone;

    private String name;

    private String gender;

    private String address;

    private String bloodGroup;

    private LocalDate birthday;

    private boolean active = true;

    private Set<Role> roles = new HashSet<>();
}
