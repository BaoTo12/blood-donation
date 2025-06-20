package com.example.blood_donation.service;

import com.example.blood_donation.dto.request.donation.DonationCreationRequest;
import com.example.blood_donation.dto.request.donation.DonationUpdateRequest;
import com.example.blood_donation.dto.response.donation.DonationResponse;
import com.example.blood_donation.entity.Appointment;
import com.example.blood_donation.entity.Donation;
import com.example.blood_donation.enumType.DonationType;
import com.example.blood_donation.exception.ResourceNotFoundException;
import com.example.blood_donation.mapper.DonationMapper;
import com.example.blood_donation.repository.AppointmentRepository;
import com.example.blood_donation.repository.DonationRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class DonationServiceImpl implements DonationService {

    DonationRepository donationRepository;
    AppointmentRepository appointmentRepository;
    DonationMapper mapper;

    @Override
    public Long createDonation(DonationCreationRequest request) {
        // Validate appointment exists
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Appointment not found with id: " + request.getAppointmentId()));

        // Check if donation already exists for this appointment
        Optional<Donation> existingDonation = donationRepository.findByAppointmentId(request.getAppointmentId());
        if (existingDonation.isPresent()) {
            throw new IllegalStateException("Donation record already exists for appointment id: " + request.getAppointmentId());
        }


        Donation donation = mapper.toDonation(request);
        Donation savedDonation = donationRepository.save(donation);
        return savedDonation.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DonationResponse> getAllDonations(Pageable pageable) {
        Page<Donation> donations = donationRepository.findAll(pageable);
        return donations.map(mapper::toDonationResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DonationResponse> getAllDonationsList() {
        List<Donation> donations = donationRepository.findAll();
        return donations.stream()
                .map(mapper::toDonationResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DonationResponse getDonationById(Long id) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation not found with id: " + id));
        return mapper.toDonationResponse(donation);
    }

    @Override
    @Transactional(readOnly = true)
    public DonationResponse getDonationByAppointmentId(Long appointmentId) {
        Donation donation = donationRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Donation not found for appointment id: " + appointmentId));
        return mapper.toDonationResponse(donation);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DonationResponse> getDonationsByType(DonationType donationType) {
        List<Donation> donations = donationRepository.findByDonationType(donationType);
        return donations.stream()
                .map(mapper::toDonationResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Long getTotalDonationAmount() {
        return donationRepository.getTotalDonationAmount();
    }

    @Override
    @Transactional(readOnly = true)
    public Map<DonationType, Long> getDonationCountByType() {
        List<Object[]> results = donationRepository.getDonationCountByType();
        return results.stream()
                .collect(Collectors.toMap(
                        result -> (DonationType) result[0],
                        result -> (Long) result[1]
                ));
    }

    @Override
    public void updateDonation(DonationUpdateRequest request, Long id) {
        Donation existingDonation = donationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Donation not found with id: " + id));

        // Validate appointment if being updated
        if (request.getAppointmentId() != null &&
                !request.getAppointmentId().equals(existingDonation.getAppointment().getId())) {

            Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Appointment not found with id: " + request.getAppointmentId()));

            // Check if another donation exists for this appointment
            Optional<Donation> existingDonationForAppointment = donationRepository.findByAppointmentId(request.getAppointmentId());
            if (existingDonationForAppointment.isPresent() &&
                    !existingDonationForAppointment.get().getId().equals(id)) {
                throw new IllegalStateException("Donation record already exists for appointment id: " + request.getAppointmentId());
            }
        }

        // Validate donation amount if being updated
        String donationType = request.getDonationType() != null ?
                request.getDonationType() : existingDonation.getDonationType();
        Integer amount = request.getAmount() != null ?
                request.getAmount() : existingDonation.getAmount();

        if (request.getDonationType() != null || request.getAmount() != null) {

        }

        mapper.updateFromDto(request, existingDonation);
        donationRepository.save(existingDonation);
    }

    @Override
    public void deleteDonation(Long id) {
        if (!donationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Donation not found with id: " + id);
        }
        donationRepository.deleteById(id);
    }


}

