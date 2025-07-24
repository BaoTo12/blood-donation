package com.example.blood_donation.dto.requests;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LogoutRequest {
    private String token;
}
