package com.example.blood_donation.service;

import com.example.blood_donation.dto.request.BloodRequest.BloodRequestCreationRequest;
import com.example.blood_donation.dto.request.BloodRequest.BloodRequestUpdateRequest;
import com.example.blood_donation.dto.response.BloodRequest.BloodRequestResponse;
import com.example.blood_donation.entity.BloodRequest;
import com.example.blood_donation.exception.ResourceNotFoundException;
import com.example.blood_donation.mapper.BloodRequestMapper;
import com.example.blood_donation.repository.BloodRequestRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BloodRequestServiceImpl implements BloodRequestService {

    BloodRequestRepository bloodRequestRepository;
    BloodRequestMapper mapper;


    @Override
    public Long createBloodRequest(BloodRequestCreationRequest request) {
        BloodRequest bloodRequest = mapper.toBloodRequest(request);
        BloodRequest savedBloodRequest = bloodRequestRepository.save(bloodRequest);
        return savedBloodRequest.getId();
    }

    @Override
    public List<BloodRequestResponse> getAllBloodRequests() {
        List<BloodRequest> bloodRequests = bloodRequestRepository.findAll();
        return bloodRequests.stream().map(mapper::toBloodRequestResponse).toList();
    }

    @Override
    public void updateBloodRequest(Long id, BloodRequestUpdateRequest request) {
        BloodRequest bloodRequest = bloodRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("There is no blood request with Id: " + id));
        mapper.updateFromDto(request, bloodRequest);
        bloodRequestRepository.save(bloodRequest);
    }
}
