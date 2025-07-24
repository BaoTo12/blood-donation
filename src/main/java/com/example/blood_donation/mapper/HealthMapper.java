package com.example.blood_donation.mapper;

import com.example.blood_donation.dto.requests.health.HealthCreationRequest;
import com.example.blood_donation.dto.requests.health.HealthUpdateRequest;
import com.example.blood_donation.dto.responses.health.HealthResponse;
import com.example.blood_donation.entity.Appointment;
import com.example.blood_donation.entity.Health;
import com.example.blood_donation.repository.AppointmentRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public abstract class HealthMapper {

    @Autowired
    protected AppointmentRepository appointmentRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "appointment", source = "appointmentId", qualifiedByName = "mapAppointment")
    public abstract Health toHealth(HealthCreationRequest request);

    @Mapping(target = "appointmentId", source = "appointment.id")
    public abstract HealthResponse toHealthResponse(Health health);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "appointment", source = "appointmentId", qualifiedByName = "mapAppointment")
    public abstract void updateFromDto(HealthUpdateRequest request, @MappingTarget Health health);

    @Named("mapAppointment")
    protected Appointment mapAppointment(Long appointmentId) {
        if (appointmentId == null) {
            return null;
        }
        return appointmentRepository.findById(appointmentId).orElse(null);
    }
}
