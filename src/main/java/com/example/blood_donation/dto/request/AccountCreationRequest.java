package com.example.blood_donation.dto.request;

import com.example.blood_donation.enumType.BloodGroup;
import com.example.blood_donation.enumType.Gender;
import com.example.blood_donation.enumType.Role;
import com.example.blood_donation.validation.EnumValue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AccountCreationRequest {
    @NotNull
    private String name;
    @NotNull
    private String email;
    @Min(value = 8)
    @NotNull
    private String password;

    private String phone;

    private Gender gender;

    private String address;

    @EnumValue(name = "role", enumClass = Role.class)
    private String role;

    @EnumValue(name = "blood_group", enumClass = BloodGroup.class)
    private String bloodGroup;
}
