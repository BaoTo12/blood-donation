package com.example.blood_donation.controller;

import com.example.blood_donation.dto.requests.BloodRequest.BloodRequestCreationRequest;
import com.example.blood_donation.dto.requests.BloodRequest.BloodRequestUpdateRequest;
import com.example.blood_donation.dto.responses.ApiResponse;
import com.example.blood_donation.dto.responses.BloodRequest.BloodRequestResponse;
import com.example.blood_donation.service.BloodRequestService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/bloodRequest")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("hasRole('STAFF')")
public class BloodRequestController {

    BloodRequestService bloodRequestService;

    @PostMapping
    public ApiResponse<Long> createBloodRequest(@RequestBody @Valid BloodRequestCreationRequest request) {
        Long id = bloodRequestService.createBloodRequest(request);
        return ApiResponse.<Long>builder().result(id).build();
    }

    @GetMapping
    public ApiResponse<List<BloodRequestResponse>> getAllBloodRequests() {
        return ApiResponse.<List<BloodRequestResponse>>builder()
                .result(bloodRequestService.getAllBloodRequests()).build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<String> updateBloodRequest(@PathVariable Long id, @RequestBody @Valid BloodRequestUpdateRequest request) {
        bloodRequestService.updateBloodRequest(id, request);
        return ApiResponse.<String>builder().result("Update Blood Request Successfully").build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteBloodRequest(@PathVariable Long id) {
        bloodRequestService.deleteBloodRequest(id);
        return ApiResponse.<String>builder().build();
    }
}
