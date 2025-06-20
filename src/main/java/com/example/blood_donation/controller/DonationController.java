package com.example.blood_donation.controller;

import com.example.blood_donation.dto.request.donation.DonationCreationRequest;
import com.example.blood_donation.dto.request.donation.DonationUpdateRequest;
import com.example.blood_donation.dto.response.ApiResponse;
import com.example.blood_donation.dto.response.donation.DonationResponse;
import com.example.blood_donation.enumType.DonationType;
import com.example.blood_donation.service.DonationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/donation")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DonationController {

    DonationService donationService;

    @GetMapping
    public ApiResponse<Page<DonationResponse>> getAllDonations(Pageable pageable) {
        return ApiResponse.<Page<DonationResponse>>builder()
                .result(donationService.getAllDonations(pageable))
                .build();
    }

    @GetMapping("/list")
    public ApiResponse<List<DonationResponse>> getAllDonationsList() {
        return ApiResponse.<List<DonationResponse>>builder()
                .result(donationService.getAllDonationsList())
                .build();
    }

    @PostMapping
    public ApiResponse<Long> createDonation(@Valid @RequestBody DonationCreationRequest request) {
        var result = donationService.createDonation(request);
        return ApiResponse.<Long>builder()
                .result(result)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<DonationResponse> getDonationById(@PathVariable Long id) {
        return ApiResponse.<DonationResponse>builder()
                .result(donationService.getDonationById(id))
                .build();
    }

    @GetMapping("/appointment/{appointmentId}")
    public ApiResponse<DonationResponse> getDonationByAppointmentId(@PathVariable Long appointmentId) {
        return ApiResponse.<DonationResponse>builder()
                .result(donationService.getDonationByAppointmentId(appointmentId))
                .build();
    }

    @GetMapping("/type/{donationType}")
    public ApiResponse<List<DonationResponse>> getDonationsByType(@PathVariable DonationType donationType) {
        return ApiResponse.<List<DonationResponse>>builder()
                .result(donationService.getDonationsByType(donationType))
                .build();
    }

    @GetMapping("/statistics/total-amount")
    public ApiResponse<Long> getTotalDonationAmount() {
        return ApiResponse.<Long>builder()
                .result(donationService.getTotalDonationAmount())
                .build();
    }

    @GetMapping("/statistics/count-by-type")
    public ApiResponse<Object> getDonationCountByType() {
        return ApiResponse.<Object>builder()
                .result(donationService.getDonationCountByType())
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<String> updateDonation(@PathVariable Long id,
                                              @Valid @RequestBody DonationUpdateRequest request) {
        donationService.updateDonation(request, id);
        return ApiResponse.<String>builder()
                .result("Donation record updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteDonation(@PathVariable Long id) {
        donationService.deleteDonation(id);
        return ApiResponse.<String>builder()
                .result("Donation record deleted successfully")
                .build();
    }
}