package com.example.blood_donation.service.impl;

import com.example.blood_donation.dto.requests.appointment.AppointmentCreationRequest;
import com.example.blood_donation.dto.requests.appointment.AppointmentUpdateRequest;
import com.example.blood_donation.dto.responses.appointment.AppointmentResponse;
import com.example.blood_donation.entity.Appointment;
import com.example.blood_donation.exception.AppException;
import com.example.blood_donation.exception.ErrorCode;
import com.example.blood_donation.mapper.AppointmentMapper;
import com.example.blood_donation.repository.AppointmentRepository;
import com.example.blood_donation.service.AppointmentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    AppointmentRepository repo;
    AppointmentMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAllAppointments() {
        return repo.findAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentResponse getAppointmentById(Long id) {
        Appointment appt = repo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCED_NOT_FOUND
                        , "Cannot find appointment with Id: " + id));
        return mapper.toResponse(appt);
    }

    @Override
    public Long createAppointment(AppointmentCreationRequest req) {
        // pre-check duplicate
        if (repo.existsByBloodRequestIdAndAccountId(req.getRequestId(), req.getAccountId())) {
            throw new AppException(ErrorCode.DUPLICATE_RESOURCE, "There is appointment with Blood Request ID: " + req.getRequestId());
        }
        Appointment entity = mapper.toAppointment(req);
        try {
            Appointment saved = repo.save(entity);
            return saved.getId();
        } catch (DataIntegrityViolationException dive) {
            // unique constraint at DB level
            log.warn("Integrity error creating appointment: {}", dive.getMessage());
            throw new AppException(ErrorCode.DUPLICATE_RESOURCE);
        }
    }

    @Override
    public void updateAppointmentStatus(Long id, AppointmentUpdateRequest req) {
        Appointment apt = repo.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCED_NOT_FOUND
                        , "Cannot find appointment with Id: " + id));
        mapper.updateStatusFromDto(req, apt);
        repo.save(apt);
    }

    @Override
    public void deleteAppointment(Long id) {
        if (!repo.existsById(id)) {
            throw new AppException(ErrorCode.RESOURCED_NOT_FOUND
                    , "Cannot find appointment with Id: " + id);
        }
        repo.deleteById(id);
    }
}
