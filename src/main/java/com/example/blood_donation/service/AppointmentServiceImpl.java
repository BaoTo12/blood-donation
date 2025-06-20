package com.example.blood_donation.service;

import com.example.blood_donation.dto.request.appointment.AppointmentCreationRequest;
import com.example.blood_donation.dto.request.appointment.AppointmentUpdateRequest;
import com.example.blood_donation.dto.response.appointment.AppointmentResponse;
import com.example.blood_donation.entity.Appointment;
import com.example.blood_donation.exception.DuplicateResourceException;
import com.example.blood_donation.exception.ResourceNotFoundException;
import com.example.blood_donation.mapper.AppointmentMapper;
import com.example.blood_donation.repository.AppointmentRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AppointmentServiceImpl implements AppointmentService{

    AppointmentRepository repo;
    AppointmentMapper mapper;

    @Override
    public List<AppointmentResponse> getAllAppointments() {
        return repo.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentResponse getAppointmentById(Long id) {
        Appointment appt = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No appointment with id " + id));
        return mapper.toResponse(appt);
    }

    @Override
    @Transactional
    public Long createAppointment(AppointmentCreationRequest req) {
        // pre-check duplicate
        if (repo.existsByBloodRequestIdAndAccountId(req.getRequestId(), req.getAccountId())) {
            throw new DuplicateResourceException(
                    "Appointment already exists for request=" + req.getRequestId() +
                            " and member=" + req.getAccountId()
            );
        }

        Appointment entity = mapper.toAppointment(req);
        try {
            log.info("Create appointment");
            Appointment saved = repo.save(entity);
            return saved.getId();
        } catch (DataIntegrityViolationException dive) {
            // unique constraint at DB level
            log.warn("Integrity error creating appointment: {}", dive.getMessage());
            throw new DuplicateResourceException(
                    "Cannot create duplicate appointment for request="
                            + req.getRequestId() + " and member=" + req.getAccountId()
            );
        }
    }

    @Override
    @Transactional
    public void updateAppointmentStatus(Long id, AppointmentUpdateRequest req) {
        Appointment apt = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No appointment with id " + id));
        mapper.updateStatusFromDto(req, apt);
        repo.save(apt);
    }

    @Override
    @Transactional
    public void deleteAppointment(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("No appointment with id " + id);
        }
        repo.deleteById(id);
    }
}
