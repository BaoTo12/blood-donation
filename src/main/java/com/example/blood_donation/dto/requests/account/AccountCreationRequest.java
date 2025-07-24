package com.example.blood_donation.dto.request.account;

import com.example.blood_donation.enumType.BloodGroup;
import com.example.blood_donation.enumType.Gender;
import com.example.blood_donation.validation.EnumValue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountCreationRequest {
    @NotNull
    private String name;
    @NotNull
    @Email
    private String email;
    @Min(value = 8)
    @NotNull
    private String password;

    private String phone;

    private Gender gender;

    private String address;

    @EnumValue(name = "blood_group", enumClass = BloodGroup.class)
    private String bloodGroup;
}
