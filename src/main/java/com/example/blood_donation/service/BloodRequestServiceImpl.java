package com.example.blood_donation.service;

import com.example.blood_donation.dto.request.BloodRequest.BloodRequestCreationRequest;
import com.example.blood_donation.dto.request.BloodRequest.BloodRequestUpdateRequest;
import com.example.blood_donation.dto.response.BloodRequest.BloodRequestResponse;
import com.example.blood_donation.entity.BloodRequest;
import com.example.blood_donation.exception.AppException;
import com.example.blood_donation.exception.ErrorCode;
import com.example.blood_donation.mapper.BloodRequestMapper;
import com.example.blood_donation.repository.BloodRequestRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
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
    @Transactional(readOnly = true)
    public List<BloodRequestResponse> getAllBloodRequests() {
        List<BloodRequest> bloodRequests = bloodRequestRepository.findAll();
        return bloodRequests.stream().map(mapper::toBloodRequestResponse).toList();
    }

    @Override
    public void updateBloodRequest(Long id, BloodRequestUpdateRequest request) {
        BloodRequest bloodRequest = bloodRequestRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.RESOURCED_NOT_FOUND, "Cannot find Blood Request with Id: " + id));
        mapper.updateFromDto(request, bloodRequest);
        bloodRequestRepository.save(bloodRequest);
    }

    @Override
    public void deleteBloodRequest(Long id) {
        bloodRequestRepository.deleteById(id);
    }
}
