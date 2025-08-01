package com.example.blood_donation.mapper;

import com.example.blood_donation.dto.requests.permission.PermissionRequest;
import com.example.blood_donation.dto.responses.permission.PermissionResponse;
import com.example.blood_donation.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);
}
