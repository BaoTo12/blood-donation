package com.example.blood_donation.dto.responses;


import lombok.Builder;

@Builder
public record ErrorResponse(int code, String message) {
}
