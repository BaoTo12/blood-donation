package com.example.blood_donation.controller;

import com.example.blood_donation.dto.request.appointment.AppointmentCreationRequest;
import com.example.blood_donation.dto.request.appointment.AppointmentUpdateRequest;
import com.example.blood_donation.dto.response.ApiResponse;
import com.example.blood_donation.dto.response.appointment.AppointmentResponse;
import com.example.blood_donation.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/appointment")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STAFF')")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppointmentController {

    AppointmentService appointmentService;

    @GetMapping
    public ApiResponse<List<AppointmentResponse>> getAllAppointments() {
        List<AppointmentResponse> all = appointmentService.getAllAppointments();
        return ApiResponse.<List<AppointmentResponse>>builder()
                .result(all)
                .build();
    }

    @PostMapping
    public ApiResponse<Long> createAppointment(@Valid @RequestBody AppointmentCreationRequest req) {
        Long id = appointmentService.createAppointment(req);
        return ApiResponse.<Long>builder()
                .result(id)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<AppointmentResponse> getAppointmentById(@PathVariable Long id) {
        AppointmentResponse dto = appointmentService.getAppointmentById(id);
        return ApiResponse.<AppointmentResponse>builder()
                .result(dto)
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<String> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentUpdateRequest req
    ) {
        appointmentService.updateAppointmentStatus(id, req);
        return ApiResponse.<String>builder()
                .result("Updated appointment successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ApiResponse.<String>builder()
                .result("Deleted appointment successfully")
                .build();
    }
}
