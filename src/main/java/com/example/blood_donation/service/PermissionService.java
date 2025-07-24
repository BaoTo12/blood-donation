package com.example.blood_donation.service;

import com.example.blood_donation.dto.requests.permission.PermissionRequest;
import com.example.blood_donation.dto.responses.permission.PermissionResponse;

import java.util.List;

public interface PermissionService {
    PermissionResponse create(PermissionRequest request);

    List<PermissionResponse> getAll();

    void delete(String permission);
}
