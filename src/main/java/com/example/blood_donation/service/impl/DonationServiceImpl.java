package com.example.blood_donation.service.impl;

import com.example.blood_donation.dto.requests.donation.DonationCreationRequest;
import com.example.blood_donation.dto.requests.donation.DonationUpdateRequest;
import com.example.blood_donation.dto.responses.donation.DonationResponse;
import com.example.blood_donation.entity.Appointment;
import com.example.blood_donation.entity.Donation;
import com.example.blood_donation.enumType.DonationType;
import com.example.blood_donation.exception.AppException;
import com.example.blood_donation.exception.ErrorCode;
import com.example.blood_donation.mapper.DonationMapper;
import com.example.blood_donation.repository.AppointmentRepository;
import com.example.blood_donation.repository.DonationRepository;
import com.example.blood_donation.service.DonationService;
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
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCED_NOT_FOUND));

        // Check if donation already exists for this appointment
        Donation existingDonation = donationRepository
                .findByAppointmentId(request.getAppointmentId())
                .orElseThrow(() -> new AppException(ErrorCode.DUPLICATE_RESOURCE
                        , "There is donation with appointmentId: " + request.getAppointmentId()));

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
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCED_NOT_FOUND, "Cannot find donation with donationId: " + id));
        return mapper.toDonationResponse(donation);
    }

    @Override
    @Transactional(readOnly = true)
    public DonationResponse getDonationByAppointmentId(Long appointmentId) {
        Donation donation = donationRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCED_NOT_FOUND, "Cannot find donation with appointmentId: " + appointmentId));
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
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCED_NOT_FOUND
                        , "Cannot find donation with donationId: " + id));

        // Validate appointment if being updated
        if (request.getAppointmentId() != null) {

            Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                    .orElseThrow(() -> new AppException(ErrorCode.RESOURCED_NOT_FOUND
                            , "Cannot find appointment with Id: " + request.getAppointmentId()));

            // Check if another donation exists for this appointment
            Optional<Donation> existingDonationForAppointment = donationRepository
                    .findByAppointmentId(appointment.getId());
            if (existingDonationForAppointment.isPresent()) {
                // TODO: temporary
                throw new AppException(ErrorCode.DUPLICATE_RESOURCE,
                        "There is another Donation with appointmentId: " + appointment.getId());
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
            throw new AppException(ErrorCode.RESOURCED_NOT_FOUND, "Cannot find donation with Id: " + id);
        }
        donationRepository.deleteById(id);
    }


}

