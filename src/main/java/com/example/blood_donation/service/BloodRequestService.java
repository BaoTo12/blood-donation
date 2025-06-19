package com.example.blood_donation.service;

import com.example.blood_donation.dto.request.BloodRequest.BloodRequestCreationRequest;

public interface BloodRequestService {
    Long createBloodRequest(BloodRequestCreationRequest request);
}
