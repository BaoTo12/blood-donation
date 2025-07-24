package com.example.blood_donation.mapper;

import com.example.blood_donation.dto.requests.appointment.AppointmentCreationRequest;
import com.example.blood_donation.dto.requests.appointment.AppointmentUpdateRequest;
import com.example.blood_donation.dto.responses.appointment.AppointmentResponse;
import com.example.blood_donation.entity.Account;
import com.example.blood_donation.entity.Appointment;
import com.example.blood_donation.entity.BloodRequest;
import com.example.blood_donation.exception.AppException;
import com.example.blood_donation.exception.ErrorCode;
import com.example.blood_donation.repository.AccountRepository;
import com.example.blood_donation.repository.BloodRequestRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        uses = {AccountMapper.class, BloodRequestMapper.class})
public abstract class AppointmentMapper {

    @Autowired
    protected AccountRepository accountRepo;      // or service if you prefer
    @Autowired
    protected BloodRequestRepository requestRepo;


    // Map creation request → new Appointment entity.
    @Mapping(target = "account", expression = "java(loadAccount(request.getAccountId()))")
    @Mapping(target = "bloodRequest", expression = "java(loadRequest(request.getRequestId()))")
    public abstract Appointment toAppointment(AppointmentCreationRequest request);


    // Map Appointment → AppointmentResponse DTO.
    @Mapping(target = "id", source = "id")
    @Mapping(target = "account", source = "account")
    @Mapping(target = "bloodRequest", source = "bloodRequest")
    @Mapping(target = "status", source = "status")
    public abstract AppointmentResponse toResponse(Appointment appointment);


    //  Update only the status field on an existing entity.

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "status", source = "status")
    public abstract void updateStatusFromDto(AppointmentUpdateRequest dto, @MappingTarget Appointment entity);

    // helpers to fetch JPA references (will throw if not found)
    protected Account loadAccount(Long memberId) {
        return accountRepo.findById(memberId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCED_NOT_FOUND));
    }

    protected BloodRequest loadRequest(Long requestId) {
        return requestRepo.findById(requestId)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCED_NOT_FOUND));
    }
}