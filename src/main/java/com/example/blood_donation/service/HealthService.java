package com.example.blood_donation.service;

import com.example.blood_donation.dto.request.health.HealthCreationRequest;
import com.example.blood_donation.dto.request.health.HealthUpdateRequest;
import com.example.blood_donation.dto.response.health.HealthResponse;

import java.util.List;

public interface HealthService {
    Long createHealthRecord(HealthCreationRequest request);

    List<HealthResponse> getAllHealthRecords();

    HealthResponse getHealthRecordById(Long id);

    HealthResponse getHealthRecordByAppointmentId(Long appointmentId);

    void updateHealthRecord(HealthUpdateRequest request, Long id);

    void deleteHealthRecord(Long id);
}
