package com.example.blood_donation.dto.response;


import lombok.Builder;

@Builder
public record ErrorResponse(int code, String message) {
}
