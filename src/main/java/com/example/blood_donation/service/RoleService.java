package com.example.blood_donation.service;

import com.example.blood_donation.dto.request.role.RoleRequest;
import com.example.blood_donation.dto.response.role.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse create(RoleRequest request);
    List<RoleResponse> getAll();
    void delete(String role);
}
