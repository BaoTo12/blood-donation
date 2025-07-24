package com.example.blood_donation.dto.responses.appointment;

import com.example.blood_donation.dto.responses.BloodRequest.BloodRequestResponse;
import com.example.blood_donation.dto.responses.account.AccountResponse;
import com.example.blood_donation.enumType.AppointmentStatus;
import com.example.blood_donation.validation.EnumValue;
import lombok.Data;


@Data
public class AppointmentResponse {

    private Long id;

    private AccountResponse account;

    private BloodRequestResponse bloodRequest;

    @EnumValue(name = "status", enumClass = AppointmentStatus.class)
    private String status;
}
