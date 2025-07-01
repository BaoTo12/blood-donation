package com.example.blood_donation.controller;

import com.example.blood_donation.dto.request.health.HealthCreationRequest;
import com.example.blood_donation.dto.request.health.HealthUpdateRequest;
import com.example.blood_donation.dto.response.ApiResponse;
import com.example.blood_donation.dto.response.health.HealthResponse;
import com.example.blood_donation.service.HealthService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/health")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("hasRole(MEMBER)")
public class HealthController {

    HealthService healthService;

    @GetMapping
    public ApiResponse<List<HealthResponse>> getAllHealthRecords() {
        return ApiResponse.<List<HealthResponse>>builder()
                .result(healthService.getAllHealthRecords())
                .build();
    }

    @PostMapping
    public ApiResponse<Long> createHealthRecord(@Valid @RequestBody HealthCreationRequest request) {
        var result = healthService.createHealthRecord(request);
        return ApiResponse.<Long>builder()
                .result(result)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<HealthResponse> getHealthRecordById(@PathVariable Long id) {
        return ApiResponse.<HealthResponse>builder()
                .result(healthService.getHealthRecordById(id))
                .build();
    }

    @GetMapping("/appointment/{appointmentId}")
    public ApiResponse<HealthResponse> getHealthRecordByAppointmentId(@PathVariable Long appointmentId) {
        return ApiResponse.<HealthResponse>builder()
                .result(healthService.getHealthRecordByAppointmentId(appointmentId))
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<String> updateHealthRecord(@PathVariable Long id,
                                                  @Valid @RequestBody HealthUpdateRequest request) {
        healthService.updateHealthRecord(request, id);
        return ApiResponse.<String>builder()
                .result("Health record updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteHealthRecord(@PathVariable Long id) {
        healthService.deleteHealthRecord(id);
        return ApiResponse.<String>builder()
                .result("Health record deleted successfully")
                .build();
    }
}
