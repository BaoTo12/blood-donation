package com.example.blood_donation.service;

import com.example.blood_donation.dto.requests.BloodRequest.BloodRequestCreationRequest;
import com.example.blood_donation.dto.requests.BloodRequest.BloodRequestUpdateRequest;
import com.example.blood_donation.dto.responses.BloodRequest.BloodRequestResponse;

import java.util.List;

public interface BloodRequestService {
    Long createBloodRequest(BloodRequestCreationRequest request);

    List<BloodRequestResponse> getAllBloodRequests();

    void updateBloodRequest(Long id, BloodRequestUpdateRequest request);

    void deleteBloodRequest(Long id);
}
