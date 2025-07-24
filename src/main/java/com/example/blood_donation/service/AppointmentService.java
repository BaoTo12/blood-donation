package com.example.blood_donation.service;

import com.example.blood_donation.dto.requests.appointment.AppointmentCreationRequest;
import com.example.blood_donation.dto.requests.appointment.AppointmentUpdateRequest;
import com.example.blood_donation.dto.responses.appointment.AppointmentResponse;

import java.util.List;

public interface AppointmentService {
    Long createAppointment(AppointmentCreationRequest request);

    List<AppointmentResponse> getAllAppointments();

    AppointmentResponse getAppointmentById(Long id);

    void updateAppointmentStatus(Long id, AppointmentUpdateRequest request);

    void deleteAppointment(Long id);
}
