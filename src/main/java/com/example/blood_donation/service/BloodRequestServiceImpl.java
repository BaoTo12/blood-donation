package com.example.blood_donation.service;

import com.example.blood_donation.dto.request.BloodRequest.BloodRequestCreationRequest;
import com.example.blood_donation.entity.BloodRequest;
import com.example.blood_donation.mapper.BloodRequestMapper;
import com.example.blood_donation.repository.BloodRequestRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BloodRequestServiceImpl implements BloodRequestService{

    BloodRequestRepository bloodRequestRepository;
    BloodRequestMapper mapper;


    @Override
    public Long createBloodRequest(BloodRequestCreationRequest request) {
        BloodRequest bloodRequest = mapper.toBloodRequest(request);
        BloodRequest savedBloodRequest = bloodRequestRepository.save(bloodRequest);
        return savedBloodRequest.getId();
    }
}
