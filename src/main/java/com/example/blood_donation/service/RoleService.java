package com.example.blood_donation.service;

import com.example.blood_donation.dto.requests.role.RoleRequest;
import com.example.blood_donation.dto.responses.role.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse create(RoleRequest request);

    List<RoleResponse> getAll();

    void delete(String role);
}
