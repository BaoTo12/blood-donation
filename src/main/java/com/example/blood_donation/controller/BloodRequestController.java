package com.example.blood_donation.controller;

import com.example.blood_donation.dto.request.BloodRequest.BloodRequestCreationRequest;
import com.example.blood_donation.dto.response.ApiResponse;
import com.example.blood_donation.service.BloodRequestService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.base-path}/bloodRequest")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BloodRequestController {

    BloodRequestService bloodRequestService;

    @PostMapping
    public ApiResponse<Long> createBloodRequest(@RequestBody @Valid BloodRequestCreationRequest request){
        Long id = bloodRequestService.createBloodRequest(request);
        return ApiResponse.<Long>builder().result(id).build();
    }
}
