package com.example.blood_donation.service;

import com.example.blood_donation.dto.request.donation.DonationCreationRequest;
import com.example.blood_donation.dto.request.donation.DonationUpdateRequest;
import com.example.blood_donation.dto.response.donation.DonationResponse;
import com.example.blood_donation.enumType.DonationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface DonationService {
    Long createDonation(DonationCreationRequest request);

    Page<DonationResponse> getAllDonations(Pageable pageable);

    List<DonationResponse> getAllDonationsList();

    DonationResponse getDonationById(Long id);

    DonationResponse getDonationByAppointmentId(Long appointmentId);

    List<DonationResponse> getDonationsByType(DonationType donationType);

    Long getTotalDonationAmount();

    Map<DonationType, Long> getDonationCountByType();

    void updateDonation(DonationUpdateRequest request, Long id);

    void deleteDonation(Long id);
}
