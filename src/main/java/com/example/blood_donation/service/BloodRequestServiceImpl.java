package com.example.blood_donation.service;

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
}
