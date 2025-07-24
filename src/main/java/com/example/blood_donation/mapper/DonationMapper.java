package com.example.blood_donation.mapper;

import com.example.blood_donation.dto.requests.donation.DonationCreationRequest;
import com.example.blood_donation.dto.requests.donation.DonationUpdateRequest;
import com.example.blood_donation.dto.responses.donation.DonationResponse;
import com.example.blood_donation.entity.Appointment;
import com.example.blood_donation.entity.Donation;
import com.example.blood_donation.repository.AppointmentRepository;
import jakarta.persistence.EntityManager;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public abstract class DonationMapper {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private EntityManager entityManager;

    @Mapping(target = "appointment", source = "appointmentId", qualifiedByName = "mapAppointment")
    public abstract Donation toDonation(DonationCreationRequest request);

    @Mapping(target = "appointmentId", source = "appointment.id")
    public abstract DonationResponse toDonationResponse(Donation donation);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "appointment", source = "appointmentId", qualifiedByName = "mapAppointment")
    public abstract void updateFromDto(DonationUpdateRequest request, @MappingTarget Donation donation);

    @Named("mapAppointment")
    protected Appointment mapAppointment(Long appointmentId) {
        if (appointmentId == null) {
            return null;
        }
        return entityManager.getReference(Appointment.class, appointmentId);
    }
}
