package com.example.blood_donation.dto.request;

import com.example.blood_donation.enumType.BloodGroup;
import com.example.blood_donation.enumType.Gender;
import com.example.blood_donation.enumType.Role;
import com.example.blood_donation.validation.EnumValue;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountUpdateRequest {

    private String name;
    private String email;

    @Min(8)
    private String password;

    private String phone;

    private Gender gender;

    private String address;

    @EnumValue(name = "blood_group", enumClass = BloodGroup.class)
    private String bloodGroup;
}
