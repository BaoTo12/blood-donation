package com.example.blood_donation.service;

import com.example.blood_donation.dto.request.health.HealthCreationRequest;
import com.example.blood_donation.dto.request.health.HealthUpdateRequest;
import com.example.blood_donation.dto.response.health.HealthResponse;
import com.example.blood_donation.entity.Appointment;
import com.example.blood_donation.entity.Health;
import com.example.blood_donation.exception.AppException;
import com.example.blood_donation.exception.ErrorCode;
import com.example.blood_donation.mapper.HealthMapper;
import com.example.blood_donation.repository.AppointmentRepository;
import com.example.blood_donation.repository.HealthRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class HealthServiceImpl implements HealthService {

    HealthRepository healthRepository;
    AppointmentRepository appointmentRepository;
    HealthMapper mapper;

    @Override
    public Long createHealthRecord(HealthCreationRequest request) {
        // Validate appointment exists if provided
        if (request.getAppointmentId() != null) {
            Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                    .orElseThrow(() -> new AppException(ErrorCode.RESOURCED_NOT_FOUND));

            // Check if health record already exists for this appointment
            Optional<Health> existingHealth = healthRepository.findByAppointmentId(request.getAppointmentId());
            if (existingHealth.isPresent()) {
                throw new IllegalStateException("Health record already exists for appointment id: " + request.getAppointmentId());
            }
        }

        Health health = mapper.toHealth(request);
        Health savedHealth = healthRepository.save(health);
        return savedHealth.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<HealthResponse> getAllHealthRecords() {
        List<Health> healthRecords = healthRepository.findAll();
        return healthRecords.stream()
                .map(mapper::toHealthResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HealthResponse getHealthRecordById(Long id) {
        Health health = healthRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCED_NOT_FOUND));
        return mapper.toHealthResponse(health);
    }

    @Override
    @Transactional(readOnly = true)
    public HealthResponse getHealthRecordByAppointmentId(Long appointmentId) {
        Health health = healthRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCED_NOT_FOUND));
        return mapper.toHealthResponse(health);
    }

    @Override
    public void updateHealthRecord(HealthUpdateRequest request, Long id) {
        Health existingHealth = healthRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCED_NOT_FOUND));

        // Validate appointment if being updated
        if (request.getAppointmentId() != null &&
                !request.getAppointmentId().equals(existingHealth.getAppointment().getId())) {

            Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                    .orElseThrow(() -> new AppException(ErrorCode.RESOURCED_NOT_FOUND));

            // Check if another health record exists for this appointment
            Optional<Health> existingHealthForAppointment = healthRepository.findByAppointmentId(request.getAppointmentId());
            if (existingHealthForAppointment.isPresent() &&
                    !existingHealthForAppointment.get().getId().equals(id)) {
                throw new IllegalStateException("Health record already exists for appointment id: " + request.getAppointmentId());
            }
        }

        mapper.updateFromDto(request, existingHealth);
        healthRepository.save(existingHealth);
    }

    @Override
    public void deleteHealthRecord(Long id) {
        if (!healthRepository.existsById(id)) {
            throw new AppException(ErrorCode.RESOURCED_NOT_FOUND);
        }
        healthRepository.deleteById(id);
    }
}
